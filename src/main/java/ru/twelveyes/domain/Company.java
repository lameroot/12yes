package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 22.01.14.
 */
@NodeEntity
public class Company extends Activity {

    private Contact contact;
    @Fetch
    @RelatedTo(type = "COMPANY", direction = Direction.BOTH)
    private Set<Activity> activities = new HashSet<Activity>();
    @Fetch @RelatedToVia(type = "RATED", direction = Direction.INCOMING)
    private Iterable<Rating> ratings;

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getStars() {
        int stars = 0, count = 0;
        for (Rating rating : ratings) {
            stars += rating.getStars(); count++;
        }
        return count == 0 ? 0 : stars / count;
    }

    private void belong(Activity activity, int deep) {
        //activities.add(activity);
        addParent(activity);
        if ( null != activity.getParents() ) {
            for (Activity parent : activity.getParents()) {
                belong(parent, ++deep);
            }
        }
    }

    public Company belongTo(Activity activity) {
        belong(activity,0);
        return this;
    }





    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Company{");
//        sb.append("id=").append(id);
//        sb.append(", title='").append(title).append('\'');
        sb.append(", activities size =").append(null != activities ? activities.size() : null);
        sb.append('}');
        return sb.toString();
    }
}
