package ru.twelveyes.config;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.twelveyes.AbstractConfigTest;
import ru.twelveyes.domain.Contact;
import ru.twelveyes.domain.Profile;
import ru.twelveyes.repository.ProfileRepository;
import ru.twelveyes.repository.TweetRepository;

import javax.annotation.Resource;
import java.util.Date;

/**
 * User: Krainov
 * Date: 09.02.14
 * Time: 13:44
 */
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes = {
        Neo4jConfig.class
})
public class Neo4jConfigTest extends AbstractConfigTest {

    @Resource
    protected ApplicationContext applicationContext;
    @Resource
    protected Neo4jTemplate template;
    @Resource
    protected ProfileRepository profileRepository;
    @Resource
    protected TweetRepository tweetRepository;

    @Test
    public void testExist() {
        assertNotNull(applicationContext);
    }

    private Contact createContact() {
        Contact contact = new Contact();
        contact.setFirstName("stas");
        contact.setSecondName("krainov");
        contact.setAddress("Russia, Moscow");
        contact.setEmails("asd@mail.ru,qwe@mail.ru");
        contact.setLatitude(11.0);
        contact.setLongitude(15.0);
        contact.setPhones("123123123,789789789");
        return contact;
    }

    protected Profile createProfile(String email, String password) {
        Profile profile = new Profile();
        profile.setEmail(email);
        profile.setLogin(email.substring(0,email.indexOf("@")));
        profile.setPassword(password);
        profile.setBirthday(new Date());
        profile.setSex(Profile.Sex.MALE);
        profile.setContact(createContact());

        return profile;
    }
}
