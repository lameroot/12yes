package ru.twelveyes.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import ru.twelveyes.domain.Activity;

/**
 * User: Krainov
 * Date: 17.02.14
 * Time: 17:56
 */
public interface ActivityRepository<A extends Activity> extends GraphRepository<A> {

//    public A findByIndex(String index);
//    public A findByTitleLike(String title);
//    public Iterable<A> findAllChildActivities(A parent);

}
