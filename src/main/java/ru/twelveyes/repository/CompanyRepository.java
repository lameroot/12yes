package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.neo4j.repository.SpatialRepository;
import ru.twelveyes.domain.Activity;
import ru.twelveyes.domain.Company;
import ru.twelveyes.domain.Contact;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 18:05
 */
public interface CompanyRepository extends ActivityRepository<Company> {

    //public Iterable<Company> findAllChildCompany(Activity parent);//поиск по атрибутам (просто поиск делать не надо)

    //public Company findByUniqueIndex(String index);
}
