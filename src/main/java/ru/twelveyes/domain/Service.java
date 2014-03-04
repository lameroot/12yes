package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Created by lameroot on 26.02.14.
 * Услуга, которую предлагает компания
 */
@NodeEntity
public class Service {

    @GraphId
    private Long id;
    private String title;
    private String description;
    private Contact contact;
    //дале детали услуги (так как цена, дни занятий итд) - или тут или в ввиде класса


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
