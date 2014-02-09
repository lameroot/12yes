package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 22.01.14.
 */
@NodeEntity
public class Activity {

    @GraphId
    private Long id;
    @GraphProperty
    private String title;
    @RelatedTo(type = "ACTIVITY", direction = Direction.INCOMING)
    private Set<Activity> parents;
    @RelatedTo(type = "ACTIVITY", direction = Direction.OUTGOING)
    private Set<Activity> child;
    @RelatedTo(type = "COMPANY", direction = Direction.BOTH)
    private Set<Company> companies;

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

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Activity addParent(Activity activity) {
        if ( null == parents ) parents = new HashSet<Activity>();
        parents.add(activity);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Activity{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", parents size=").append(null != parents ?  parents.size() : null);
        sb.append(", child size =").append(null != child ?  child.size() : null);
        sb.append(", companies size =").append(null != companies ? companies.size() : null);
        sb.append('}');
        return sb.toString();
    }
}