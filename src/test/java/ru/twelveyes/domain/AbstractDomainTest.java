package ru.twelveyes.domain;

import ru.twelveyes.config.Neo4jConfigTest;

import java.util.Date;

/**
 * Created by lameroot on 12.01.14.
 */
public class AbstractDomainTest extends Neo4jConfigTest {

    protected Profile createProfile() {
        Profile profile = new Profile();
        profile.setSex(Profile.Sex.MALE);
        profile.setBirthday(new Date());
        profile.setEmail("asd@asd.ru");
        profile.setPassword("123123");

        return profile;
    }

    protected Contact createContact() {
        Contact contact = new Contact();
        contact.setFirstName("stas");
        contact.setSecondName("krainov");
        contact.setAddress("address");
        contact.setEmails("asd@asd.ru");
        contact.setPhones("123123");
        contact.setLatitude(111.2);
        contact.setLongitude(333.2);

        return contact;
    }

    protected MediaContent createMediaContent() {
        MediaContent content = new MediaContent();
        content.setContentType("image");
        content.setModerated(false);
        content.setData("/var/images/1.jpg");
        content.setSize(1111);
        content.setType(MediaContent.MediaType.PHOTO);
        content.setUploadAt(new Date());

        return content;
    }


    protected YesLine createYesLine() {
        YesLine line = new YesLine();
        line.setTime(1000000);
        line.setBudget(10000.0);
        line.setStartAt(new Date());

        return line;
    }

    protected YesPeriod createYesPeriod() {
        YesPeriod period = new YesPeriod();
        period.setTitle("this is test yes period");
        period.setDescription("this is test description");

        return period;
    }
}
