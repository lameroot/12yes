package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.*;
import ru.twelveyes.domain.Profile;
import ru.twelveyes.domain.Yes;

/**
 * Created by lameroot on 22.01.14.
 */
@RelationshipEntity(type = "COMMENTED")
public class Comment {

    @GraphId
    private Long id;
    @StartNode
    private Profile profile;
    @EndNode
    private Yes yes;
    private int stars;
    private String comment;

    public Comment comment(Integer stars, String comment) {
        this.stars = stars;
        this.comment = comment;
        return this;
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

    public Yes getYes() {
        return yes;
    }

    public void setYes(Yes yes) {
        this.yes = yes;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
