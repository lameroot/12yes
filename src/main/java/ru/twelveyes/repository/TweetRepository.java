package ru.twelveyes.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import ru.twelveyes.domain.Profile;
import ru.twelveyes.domain.Tweet;

import java.util.Collection;
import java.util.Set;

/**
 * Created by lameroot on 16.02.14.
 */
public interface TweetRepository extends GraphRepository<Tweet>{

    //public Set<Profile> findAllWatchers(Tweet tweet);

    @Query("start me=node:logins(login={0}) " +
            "MATCH me-[:POSTED]->tweet " +
            "RETURN tweet")
    public Set<Tweet> findAllByProfileLogin(String login);
    public Collection<Tweet> findByTagsTag(String tag);
}
