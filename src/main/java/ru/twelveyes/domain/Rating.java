package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import ru.twelveyes.domain.Profile;

/**
 * Created by lameroot on 22.01.14.
 */
@RelationshipEntity(type = "RATED")
public class Rating {

    @GraphId
    private Long id;
    @StartNode
    private Profile graduate;
    @EndNode
    private Company company;
    private Integer stars;
    private String comment;

    public Rating rate(Integer stars, String comment) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Profile getGraduate() {
        return graduate;
    }

    public void setGraduate(Profile graduate) {
        this.graduate = graduate;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rating{");
        sb.append("company=").append(company);
        sb.append(", graduate=").append(graduate);
        sb.append(", stars=").append(stars);
        sb.append(", comment='").append(comment).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
