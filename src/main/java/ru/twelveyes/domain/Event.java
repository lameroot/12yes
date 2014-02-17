package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 16.02.14.
 * Событие, например это может быть концерт, либо спортивное мероприятие, которые также могут попадать в твитленту через какой-ниб разделитель
 */
@NodeEntity
public class Event {

    @GraphId
    private Long id;
    private String title;
    @RelatedTo(type = "EVENT_ACTIVITY" , direction = Direction.OUTGOING)
    private Set<Activity> activities = new HashSet<>();
    private Date date;
    private Date createdAt;

    public Event addActivity(Activity activity) {
        activities.add(activity);
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private void belong(Activity activity, int deep) {
        //activities.add(activity);
        addActivity(activity);
        if ( null != activity.getParents() ) {
            for (Activity parent : activity.getParents()) {
                belong(parent, ++deep);
            }
        }
    }

    public Event belongTo(Activity activity) {
        belong(activity,0);
        return this;
    }
}
