package ru.twelveyes.service;

import org.junit.Test;
import ru.twelveyes.AbstractConfigTest;
import ru.twelveyes.config.Neo4jConfigTest;
import ru.twelveyes.domain.Activity;
import ru.twelveyes.domain.Company;
import ru.twelveyes.domain.Contact;
import ru.twelveyes.domain.ContactParam;

import javax.annotation.Resource;

/**
 * User: Krainov
 * Date: 03.03.14
 * Time: 11:17
 */
public class CompanyServiceTest extends Neo4jConfigTest{

    @Resource
    protected CompanyService companyService;

    @Test
    public void testCreate() throws Exception {
        Activity sport = createActivity("sport",null);
        activityRepository.save(sport);
        assertNotNull(sport.getId());

        Activity culture = createActivity("culture",null);
        activityRepository.save(culture);
        assertNotNull(culture.getId());

        Activity dance = createActivity("dance",sport);
        activityRepository.save(dance);
        assertNotNull(dance);

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
    }
}
