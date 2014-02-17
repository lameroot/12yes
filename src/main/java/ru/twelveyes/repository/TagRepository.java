package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import ru.twelveyes.domain.Tag;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 18:09
 */
public interface TagRepository extends GraphRepository<Tag> {
}
