package ru.twelveyes.config;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.twelveyes.AbstractConfigTest;
import ru.twelveyes.domain.*;
import ru.twelveyes.repository.*;
import ru.twelveyes.service.ProfileService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

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
    @Resource
    protected ActivityRepository activityRepository;
    @Resource
    protected CompanyRepository companyRepository;
    @Resource
    protected TagRepository tagRepository;
    @Resource
    protected EventRepository eventRepository;
    @Resource
    protected ProfileService profileService;

    @Test
    public void testExist() {
        assertNotNull(applicationContext);
    }

    protected Event createEvent(String title, Activity activity) {
        Event event = new Event();
        event.setTitle(title);
        event.belongTo(activity);
        return event;
    }


    protected Tag createTag(String tagValue) {
        Tag tag = new Tag(tagValue);
        return tag;
    }

    protected Company createCompany(String title, Activity activity) {
        Company company = new Company();
        company.setTitle(title);
        company.belongTo(activity);
        return company;
    }

    protected Activity createActivity(String title, Activity parent) {
        Activity activity = new Activity();
        activity.setTitle(title);
        activity.setIndex(title);
        activity.addParent(parent);

        return activity;
    }

    protected Contact createContact() {
        Contact contact = new Contact();
//        contact.setFirstName("stas");
//        contact.setSecondName("krainov");
//        contact.setAddress("Russia, Moscow");
//        contact.setEmails("asd@mail.ru,qwe@mail.ru");
//        contact.setLatitude(11.0);
//        contact.setLongitude(15.0);
//        contact.setPhones("123123123,789789789");
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
