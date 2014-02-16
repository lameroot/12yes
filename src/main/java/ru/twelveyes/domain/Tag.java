package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * User: Krainov
 * Date: 14.02.14
 * Time: 18:13
 */
@NodeEntity
public class Tag {

    @GraphId
    private Long id;
    @Indexed(unique = true)
    private String tag;

    public Tag(){}
    public Tag(String tag){
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tag{");
        sb.append("id=").append(id);
        sb.append(", tag='").append(tag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
