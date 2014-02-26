package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

/**
 * Created by lameroot on 26.02.14.
 * Детали услуги, такие как цена, дни занятий итп.
 *
 */
@RelationshipEntity(type = "SERVICE")
public class ServiceDetails {

    @GraphId
    private Long id;
    private @StartNode Company company;
    private @EndNode Service service;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
