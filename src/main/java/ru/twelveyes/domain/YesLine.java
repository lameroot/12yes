package ru.twelveyes.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lameroot on 28.12.13.
 */
@Entity
@Table(name = "yes_line")
public class YesLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_profile_id",nullable = false,updatable = false)
    private Profile profile;
    private long time;
    private Double budget;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "yesLine")
    private List<Yes> yeses;
    @Column(name = "created_at",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Column(name = "start_at",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;
    @Column(name = "is_public")
    private boolean isPublic;
    @Column(name = "is_comment_allowed")
    private boolean isCommentAllowed;

    public Date getStartAt() {
        return startAt;
    }

    public Integer getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public List<Yes> getYeses() {
        return yeses;
    }

    public void setYeses(List<Yes> yeses) {
        this.yeses = yeses;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isCommentAllowed() {
        return isCommentAllowed;
    }

    public void setCommentAllowed(boolean isCommentAllowed) {
        this.isCommentAllowed = isCommentAllowed;
    }

}
