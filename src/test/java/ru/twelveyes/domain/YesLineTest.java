package ru.twelveyes.domain;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by lameroot on 12.01.14.
 */
public class YesLineTest extends AbstractDomainTest {

    @Test
    @Rollback(false)
    public void testCreateYesLineWithComment() throws Exception {
        YesLine line = createYesLine();
        Profile profile = createProfile();
        entityManager.persist(profile);
        assertNotNull(profile.getId());
        line.setProfile(profile);

        entityManager.persist(line);
        assertNotNull(line.getId());

        Profile profile1 = createProfile();
        profile.setEmail("author@asd.ru");
        entityManager.persist(profile1);
        assertNotNull(profile1.getId());



    }


}
