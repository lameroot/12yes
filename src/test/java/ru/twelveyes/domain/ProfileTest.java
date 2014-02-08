package ru.twelveyes.domain;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import ru.twelveyes.config.MysqlEntityManagerConfigTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lameroot on 11.01.14.
 */
public class ProfileTest extends AbstractDomainTest{

    @Test
    @Rollback(value = false)
    public void testCreateProfile() throws Exception {
        Profile profile = createProfile();

        entityManager.persist(profile);
        assertNotNull(profile.getId());
    }

    @Test
    @Rollback(value = false)
    public void testUpdateProfile() throws Exception {
        Profile profile = createProfile();
        entityManager.persist(profile);
        assertNotNull(profile.getId());

        Profile profile1 = entityManager.find(Profile.class,profile.getId());
        assertNotNull(profile1);
        profile1.setEmail("updated@asd.ru");
        entityManager.persist(profile1);
        assertEquals("updated@asd.ru",profile1.getEmail());
    }

    @Test
    @Rollback(false)
    public void testDeleteProfile() throws Exception {
        Profile profile = createProfile();
        entityManager.persist(profile);
        assertNotNull(profile.getId());

        entityManager.remove(profile);
        Profile profile1 = entityManager.find(Profile.class,profile.getId());
        assertNull(profile1);
    }

    @Test
    @Rollback(false)
    public void testCreateProfileWithContact() throws Exception {
        Contact contact = createContact();
        entityManager.persist(contact);
        assertNotNull(contact.getId());

        Profile profile = createProfile();
        profile.setContact(contact);
        entityManager.persist(profile);
        assertNotNull(profile.getId());
        assertNotNull(profile.getContact());
    }

    @Test
    @Rollback(false)
    public void testCreateProfileWithContactAndAvatar() throws Exception {
        Contact contact = createContact();
        MediaContent avatar = createMediaContent();
        entityManager.persist(avatar);
        assertNotNull(avatar.getId());
        contact.setAvatar(avatar);
        entityManager.persist(contact);
        assertNotNull(contact);

        Contact contact1 = entityManager.find(Contact.class,contact.getId());
        assertNotNull(contact1);
        assertNotNull(contact1.getAvatar());

        Profile profile = createProfile();
        profile.setContact(contact);
        entityManager.persist(profile);
        assertNotNull(profile.getId());
        assertNotNull(profile.getContact());
    }

    @Test
    @Rollback(false)
    public void testCreateProfileWithContactAndContents() throws Exception {
        Contact contact = createContact();
        MediaContent content1 = createMediaContent();
        MediaContent content2 = createMediaContent();
        MediaContent content3 = createMediaContent();
        entityManager.persist(content1);
        entityManager.persist(content2);
        entityManager.persist(content3);
        assertNotNull(content1.getId());
        assertNotNull(content2.getId());
        assertNotNull(content3.getId());

        List<MediaContent> contents = new ArrayList<MediaContent>();
        contents.add(content1);
        contents.add(content2);
        contents.add(content3);
        contact.setContents(contents);
        entityManager.persist(contact);
        assertNotNull(contact);
        assertNotNull(contact.getContents());

        Contact contact1 = entityManager.find(Contact.class,contact.getId());
        assertNotNull(contact1);
        assertNotNull(contact1.getContents());

        Profile profile = createProfile();
        profile.setContact(contact);
        entityManager.persist(profile);
        assertNotNull(profile.getId());
        assertNotNull(profile.getContact());

        MediaContent mediaContent1 = profile.getContact().getContents().get(0);
        assertTrue(content1.equals(mediaContent1));
        MediaContent mediaContent2 = profile.getContact().getContents().get(1);
        assertTrue(content2.equals(mediaContent2));
        MediaContent mediaContent3 = profile.getContact().getContents().get(2);
        assertTrue(content3.equals(mediaContent3));
    }
}
