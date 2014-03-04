package ru.twelveyes.service;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import ru.twelveyes.AbstractConfigTest;
import ru.twelveyes.config.Neo4jConfigTest;
import ru.twelveyes.domain.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * User: Krainov
 * Date: 03.03.14
 * Time: 11:17
 */
public class CompanyServiceTest extends Neo4jConfigTest{

    @Resource
    protected CompanyService companyService;
    @Resource ActivityService<Activity> activityService;
    Activity sport;
    Activity culture;
    Activity dance;

    private void prepareActivities() {
        sport = createActivity("sport",null);
        sport = activityService.create(sport,sport.getTitle(),sport.getUniqueIndex(),null,null);
//        activityRepository.save(sport);
        assertNotNull(sport.getId());

        culture = createActivity("culture",null);
        culture = activityService.create(culture,culture.getTitle(),culture.getUniqueIndex(),null,null);
//        activityRepository.save(culture);
        assertNotNull(culture.getId());

        dance = createActivity("dance",sport);
        dance = activityService.create(dance,dance.getTitle(),dance.getUniqueIndex(),dance.getParents().toArray(new Activity[]{}));
        //activityRepository.save(dance);
        assertNotNull(dance);

    }

    @Test
    //@Rollback(false)
    public void testCreate() throws Exception {
        prepareActivities();
        Activity tango = createActivity("tango",dance);
        activityRepository.save(tango);

        Contact contact = new Contact();
        contact.addContactParam(ContactParam.ADDRESS_PARAM,"Moscow city, Lenina street, 21", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.CITY_PARAM,"Moscow", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.EMAIL_PARAM,"dontango@mail.ru", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.PHONE_PARAM,"+79267796753",ContactParam.Type.ADDRESS);
        contact.setLocation(24.0,55.0);

        Company company = companyService.create("Дом Танго","domtango",contact,tango);
        assertNotNull(company);
        assertNotNull(company.getId());

        Company foundCompany = companyService.findByIndex("domtango");
        assertNotNull(foundCompany);
        assertEquals(company.getId(),foundCompany.getId());

        //create second company
        Contact contact2 = new Contact();
        contact2.addContactParam(ContactParam.ADDRESS_PARAM,"Moscow city, Lenina street, 27", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.CITY_PARAM,"Moscow", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.EMAIL_PARAM,"breakdance@mail.ru", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.PHONE_PARAM,"+7926777777",ContactParam.Type.ADDRESS);
        contact2.setLocation(34.0,56.0);

        Company company2 = companyService.create("Школа Брэйк данса","breckdanceshcoll",contact2,dance);
        assertNotNull(company2);
        assertNotNull(company2.getId());

        Company foundCompany2 = companyService.findByIndex("breckdanceshcoll");
        assertNotNull(foundCompany2);
        assertEquals(company2.getId(),foundCompany2.getId());
    }

    @Test
    public void testUpdate() throws Exception {
        prepareActivities();
        Contact contact2 = new Contact();
        contact2.addContactParam(ContactParam.ADDRESS_PARAM,"Moscow city, Lenina street, 27", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.CITY_PARAM,"Moscow", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.EMAIL_PARAM,"breakdance@mail.ru", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.PHONE_PARAM,"+7926777777",ContactParam.Type.ADDRESS);
        contact2.setLocation(34.0,56.0);

        Company company2 = companyService.create("Школа Брэйк данса","breckdanceshcoll",contact2,dance);
        assertNotNull(company2);
        assertNotNull(company2.getId());

        Company foundCompany2 = companyService.findByIndex("breckdanceshcoll");
        assertNotNull(foundCompany2);
        assertEquals(company2.getId(),foundCompany2.getId());


        companyService.fetchContact(foundCompany2);
        foundCompany2.getContact().updateContactParam(ContactParam.CITY_PARAM,"Ivanovo");
        companyService.update(foundCompany2);

        Company foundUpdatedCompany2 = companyService.findByIndex("breckdanceshcoll");
        assertNotNull(foundUpdatedCompany2);
        assertEquals(company2.getId(), foundUpdatedCompany2.getId());
        companyService.fetchContact(foundUpdatedCompany2);
        assertEquals("Ivanovo", foundUpdatedCompany2.getContact().getContactParam(ContactParam.CITY_PARAM).getValue());
    }

    @Test
    public void testDelete() throws Exception {
        prepareActivities();
        Contact contact2 = new Contact();
        contact2.addContactParam(ContactParam.ADDRESS_PARAM,"Moscow city, Lenina street, 27", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.CITY_PARAM,"Moscow", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.EMAIL_PARAM,"breakdance@mail.ru", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.PHONE_PARAM,"+7926777777",ContactParam.Type.ADDRESS);
        contact2.setLocation(34.0,56.0);

        Company company2 = companyService.create("Школа Брэйк данса","breckdanceshcoll",contact2,dance);
        assertNotNull(company2);
        assertNotNull(company2.getId());

        Company foundCompany2 = companyService.findByIndex("breckdanceshcoll");
        assertNotNull(foundCompany2);
        assertEquals(company2.getId(),foundCompany2.getId());

        companyService.delete(foundCompany2);
        Company foundDeletedCompany2 = companyService.findByIndex("breckdanceshcoll");
        assertNull(foundDeletedCompany2);
    }

    @Test
    public void testCreateServiceDetail() throws Exception {
        prepareActivities();
        Contact contact2 = new Contact();
        contact2.addContactParam(ContactParam.ADDRESS_PARAM,"Moscow city, Lenina street, 27", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.CITY_PARAM,"Moscow", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.EMAIL_PARAM,"breakdance@mail.ru", ContactParam.Type.ADDRESS)
                .addContactParam(ContactParam.PHONE_PARAM,"+7926777777",ContactParam.Type.ADDRESS);
        contact2.setLocation(34.0,56.0);

        Company company2 = companyService.create("Школа Брэйк данса","breckdanceshcoll",contact2,dance);
        assertNotNull(company2);
        assertNotNull(company2.getId());

        Service service = new Service();
        service.setTitle("test service");
        service.setDescription("test dscriptions");
        template.save(service);

        Service service2 = new Service();
        service2.setTitle("test service2");
        service2.setDescription("test dscriptions2");
        template.save(service2);

        company2.addService(template,service,0.0,100.0, ServiceDetail.WorkingDay.ALL_WEEK);
        company2.addService(template,service2,54.0,87.0, ServiceDetail.WorkingDay.FRIDAY,ServiceDetail.WorkingDay.MONDAY);

        for (ServiceDetail serviceDetail : company2.getServices()) {
            System.out.println(serviceDetail.getCompany() + ":" + serviceDetail.getService());
        }
    }
}
