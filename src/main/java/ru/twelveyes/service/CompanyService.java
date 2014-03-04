package ru.twelveyes.service;

import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import ru.twelveyes.domain.Activity;
import ru.twelveyes.domain.Company;
import ru.twelveyes.domain.Contact;
import ru.twelveyes.repository.CompanyRepository;

import javax.annotation.Resource;

/**
 * User: Krainov
 * Date: 27.02.14
 * Time: 17:55
 */
@Service
public class CompanyService extends ActivityService<Company> {

    @Resource
    private CompanyRepository companyRepository;

    public Company create(String title, String uniqueIndex, Contact contact, Activity...parents) {
        if ( null == contact ) return null;
        Company company = create(title, uniqueIndex, parents);
        companyRepository.save(company);
        template.save(contact);
        company.setContact(contact);
        companyRepository.save(company);
        return company;
    }

    public Company findByIndex(String index) {
        return companyRepository.findByIndex(index);
    }
}
