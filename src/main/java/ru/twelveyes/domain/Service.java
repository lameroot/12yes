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
    private Company company;
    private String title;
    //дале детали услуги (так как цена, дни занятий итд) - или тут или в ввиде класса
}
