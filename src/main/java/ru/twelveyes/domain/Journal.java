package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * User: Krainov
 * Date: 14.02.14
 * Time: 18:08
 * Дневник пользователя
 */
@NodeEntity
public class Journal {

    @GraphId
    private Long id;
    @RelatedTo(direction = Direction.INCOMING)
    private Profile owner;
    @GraphProperty
    private boolean isPublic;
    private MediaContent text;

    public Profile getOwner() {
        return owner;
    }

    public void setOwner(Profile owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public MediaContent getText() {
        return text;
    }

    public void setText(MediaContent text) {
        this.text = text;
    }
}
