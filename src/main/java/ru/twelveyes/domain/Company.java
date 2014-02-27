package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

/**
 * Created by lameroot on 22.01.14.
 */
@NodeEntity
public class Company extends Activity implements Contactable{

    @RelatedTo(type = "CONTACT",direction = Direction.OUTGOING)
    private Contact contact;
    @Fetch @RelatedToVia(type = "RATED", direction = Direction.INCOMING)
    private Iterable<Rating> ratings;
    @RelatedToVia(direction = Direction.OUTGOING, type = "SERVICE")
    private Set<ServiceDetails> services;

    @Override
    public Long getOwnerId() {
        return getId();
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
        sb.append("id=").append(getId());
        sb.append(", title='").append(getTitle()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
