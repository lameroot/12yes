package ru.twelveyes.domain;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by lameroot on 23.01.14.
 */
public class ActivityCompanyTest extends AbstractDomainTest {

    @Test
    @Rollback(false)
    public void testCreateActivity() {
        Activity activity = new Activity();
        activity.setTitle("activity1");
        //todo: save
        assertNotNull(activity.getId());

        Contact contact = new Contact();
        contact.setLatitude(22.0);
        contact.setLongitude(55.0);
        assertNotNull(contact.getId());

        Company company = new Company();
        company.setTitle("company1");
        company.setContact(contact);
        assertNotNull(company.getId());



    }
}
