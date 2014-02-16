package ru.twelveyes.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.repository.CrudRepository;
import ru.twelveyes.domain.Profile;

import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
public interface ProfileRepository extends GraphRepository<Profile>, RelationshipOperationsRepository<Profile> {

    @Query(value = "start profile=node({0}) MATCH (profile)-[:FOLLOWED*]-(founder) where founder.email={1} RETURN founder")
    public Set<Profile> findProfilesByEmail(Profile profile, String email);

    @Query("start profile=node({0}) return profile")
    public Profile findById(Long id);

    @Query("start profile=node:emails(email={0}) return profile")
    public Profile findByEmail(String email);

    @Query("start me=node:Profile(login={0}) " +
            "MATCH me <-[:FOLLOWS]- follower " +
            "RETURN follower")
    public Set<Profile> findAllFollowers(Profile target);



}
