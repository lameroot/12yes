package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import ru.twelveyes.domain.Company;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 18:05
 */
public interface CompanyRepository extends GraphRepository<Company>, RelationshipOperationsRepository<Company>{
}
