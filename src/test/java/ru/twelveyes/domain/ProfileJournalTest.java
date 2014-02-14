package ru.twelveyes.domain;

import org.junit.Test;
import org.neo4j.helpers.collection.MapUtil;
import ru.twelveyes.config.Neo4jConfigTest;
import ru.twelveyes.repository.ProfileRepository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

import static org.neo4j.cypherdsl.CypherQuery.*;
import static org.neo4j.cypherdsl.querydsl.CypherQueryDSL.*;

/**
 * User: Krainov
 * Date: 14.02.14
 * Time: 18:21
 */
public class ProfileJournalTest extends Neo4jConfigTest {

    @Resource
    protected ProfileRepository profileRepository;

    @Test
    public void testExists() {
        assertNotNull(profileRepository);
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
        profile.setPassword(password);
        profile.setBirthday(new Date());
        profile.setSex(Profile.Sex.MALE);
        profile.setContact(createContact());

        return profile;
    }

    @Test
    public void testCreateProfile() {
        Profile profile = createProfile("lameroot@mail.ru","password");
        template.save(profile);
        assertNotNull(profile.getId());

        Profile foundProfile = template.findOne(profile.getId(),Profile.class);
        assertNotNull(foundProfile);

        assertEquals(profile.getEmail(),foundProfile.getEmail());
    }

    @Test
    public void testSeveralProfilesAndRelationshipBetweenThem() {
        Profile profile1 = createProfile("lameroot@mail.ru","password");
        template.save(profile1);
        assertNotNull(profile1.getId());

        Profile profile2 = createProfile("profile2@mail.ru","profile2");
        template.save(profile2);
        assertNotNull(profile2.getId());

        Profile profile3 = createProfile("profile3@mail.ru","profile3");
        template.save(profile3);
        assertNotNull(profile3.getId());

        Profile profile4 = createProfile("profile4@mail.ru","profile4");
        template.save(profile4);
        assertNotNull(profile4.getId());

        profile3.addFollower(profile4);
        profile2.addFollower(profile3);
        //profile1.addFollower(profile2);

        template.save(profile4);
        template.save(profile3);
        template.save(profile2);
        template.save(profile1);

        /*
        assertNotNull(profile1.getFollowers());
        assertNotNull(profile2.getFollowers());
        assertNotNull(profile3.getFollowers());

        System.out.println(profile1.getFollowers());
        for (Profile profile22 : profile1.getFollowers()) {
            System.out.println(profile22.getFollowers());
            for (Profile profile33 : profile22.getFollowers()) {
                System.out.println(profile33.getFollowers());
                for (Profile profile44 : profile33.getFollowers()) {
                    System.out.println(profile44.getFollowers());
                }
            }
        }
        */

        logger.debug("test");

        Set<Profile> profiles = profileRepository.findProfilesByEmail(profile1, "profile4");
        assertNotNull(profiles);
        System.out.println("size = " + profiles.size());
        for (Profile profile : profiles) {
            System.out.println("### = " + profile);
        }
        Profile profile111 = profileRepository.findByPropertyValue("password", "profile4");
        System.out.println("prof111 = " + profile111);
        //Profile profile = profileRepository.findByEmail("profile4@mail.ru");
        Profile profile = profileRepository.findById(4L);
        System.out.println(profile);
        assertNotNull(profile);
        Profile profile5 = profileRepository.findOne(4L);
        System.out.println(profile5);


        //template.query("start p=node({profile}) MATCH p-[]-other WHERE other.email={0} RETURN founder", MapUtil.map());
    }
}
