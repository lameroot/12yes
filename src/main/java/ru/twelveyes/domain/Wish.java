package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 22.01.14.
 */
public class Wish {

    private Long id;
    private Profile profile;
    private Activity activity;
    private String params;//parameters (like cost, days etc = json)
    private Date createdAt = new Date();
    private Date competedAt;
    @RelatedTo(type = "FOLLOW", direction = Direction.INCOMING)
    private Set<Profile> followers;

    public boolean isCompleted() {
        return null != competedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCompetedAt() {
        return competedAt;
    }

    public void setCompetedAt(Date competedAt) {
        this.competedAt = competedAt;
    }

    public Wish follow(Profile profile) {
        if ( null == followers ) this.followers = new HashSet<Profile>();
        followers.add(profile);
        return this;
    }
}
