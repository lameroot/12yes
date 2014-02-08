package ru.twelveyes.domain;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by lameroot on 13.01.14.
 */
public class YesPeriodTest extends AbstractDomainTest {

    @Test
    @Rollback(false)
    public void testCreateYesPeriod() throws Exception {
        YesPeriod period = createYesPeriod();
        Profile profile = createProfile();
        entityManager.persist(profile);
        assertNotNull(profile.getId());
        period.setProfile(profile);
        period.setPeriodType(YesPeriod.PeriodType.YEAR);

        YesLine line = createYesLine();
        line.setProfile(profile);
        entityManager.persist(line);
        assertNotNull(line.getId());

        YesLine line2 = createYesLine();
        line2.setPublic(true);
        line2.setProfile(profile);
        entityManager.persist(line2);
        assertNotNull(line2.getId());

        period.addYesLine(line).addYesLine(line2);
        entityManager.persist(period);
        assertNotNull(period.getId());
    }

    @Test
    public void testGetLines() {
        YesPeriod period = entityManager.find(YesPeriod.class,1);
        assertNotNull(period);
        for (YesLine line : period.getYesLines()) {
            System.out.println(line.getId());
        }
    }
}
