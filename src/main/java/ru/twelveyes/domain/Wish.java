package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.RelatedTo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 22.01.14.
 */
@Entity
@Table(name = "wish")
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_profile_id", nullable = false, updatable = false)
    private Profile profile;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_activity_id", nullable = false)
    private Activity activity;
    @Column(name = "params")
    private String params;//parameters (like cost, days etc = json)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at",insertable = true,updatable = false)
    private Date createdAt = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "completed_at",insertable = false,updatable = true)
    private Date competedAt;
    @RelatedTo(type = "FOLLOW", direction = Direction.INCOMING)
    @Transient
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
