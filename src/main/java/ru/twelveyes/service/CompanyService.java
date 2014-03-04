package ru.twelveyes.service;

import org.springframework.stereotype.Service;
import ru.twelveyes.domain.Activity;
import ru.twelveyes.domain.Company;
import ru.twelveyes.domain.Contact;
import ru.twelveyes.domain.ServiceDetail;
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
        Company company = create(new Company(),title, uniqueIndex, parents);

        companyRepository.save(company);
        template.save(contact);
        company.setContact(contact);
        companyRepository.save(company);
        return company;
    }

    public void fetchContact(Company company) {
        if ( null == company ) return;
        template.fetch(company.getContact());
    }

    public Company update(Company company) {
        return super.update(company);
    }

    public Company findByIndex(String index) {
        //return companyRepository.findByUniqueIndex(index);
        return super.findByIndex(index);
    }

    public Company addService(Company company, ServiceDetail serviceDetail) {
        return null;
    }
}
