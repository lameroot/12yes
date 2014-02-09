package ru.twelveyes.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by lameroot on 28.12.13.
 */
public class YesLine {

    private Integer id;
    private Profile profile;
    private long time;
    private Double budget;
    private List<Yes> yeses;
    private Date createdAt = new Date();
    private Date startAt;
    private boolean isPublic;
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
