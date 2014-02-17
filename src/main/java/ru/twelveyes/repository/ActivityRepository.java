package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import ru.twelveyes.domain.Activity;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 17:56
 */
public interface ActivityRepository extends GraphRepository<Activity> {
}
