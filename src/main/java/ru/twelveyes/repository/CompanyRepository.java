package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import ru.twelveyes.domain.Activity;
import ru.twelveyes.domain.Company;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 18:05
 */
public interface CompanyRepository extends GraphRepository<Company>, RelationshipOperationsRepository<Company>, ActivityRepository<Company> {

    public Iterable<Company> findAllChildCompany(Activity parent);//поиск по атрибутам (просто поиск делать не надо)
}
