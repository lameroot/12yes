package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import ru.twelveyes.domain.Event;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 18:19
 */
public interface EventRepository extends GraphRepository<Event> {
}
