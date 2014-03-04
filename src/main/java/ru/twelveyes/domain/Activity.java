package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 22.01.14.
 */
@NodeEntity
public class Activity {

    public static final String INDEX_PARAM = "index";

    @GraphId
    private Long id;
    @GraphProperty
    @Indexed
    private String title;
    @GraphProperty
    @Indexed(unique = true,indexType = IndexType.LABEL)
    protected String uniqueIndex; //индекс который формируется при создании, далее является опозновательным знаком для компаний как например %company
    @RelatedTo(type = "ACTIVITY", direction = Direction.INCOMING)
    private Set<Activity> parents;
    @RelatedTo(type = "ACTIVITY", direction = Direction.OUTGOING)
    private Set<Activity> child;
    @RelatedTo(type = "ACTIVITY_FOLLOWED", direction = Direction.INCOMING)
    private Set<Profile> followers = new HashSet<>();//компании могут вести некий блог, как в рамках своей компании, так и
    //на уровне активити, тем самым люди которые подписаны на данные активити могу видеть, что появилось что-то новое

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

    public Set<Activity> getParents() {
        return parents;
    }

    public void setParents(Set<Activity> parents) {
        this.parents = parents;
    }

    public Set<Activity> getChild() {
        return child;
    }

    public void setChild(Set<Activity> child) {
        this.child = child;
    }

    public Set<Profile> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Profile> followers) {
        this.followers = followers;
    }

    public Activity addParent(Activity activity) {
        if ( null == activity ) return this;
        if ( null == parents ) parents = new HashSet<Activity>();
        parents.add(activity);
        return this;
    }

    public String getUniqueIndex() {
        return uniqueIndex;
    }

    public void setUniqueIndex(String index) {
        this.uniqueIndex = index;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Activity{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", parents size=").append(null != parents ?  parents.size() : null);
        sb.append(", child size =").append(null != child ?  child.size() : null);
        sb.append('}');
        return sb.toString();
    }
}