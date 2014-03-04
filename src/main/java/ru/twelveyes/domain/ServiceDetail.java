package ru.twelveyes.domain;

import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;
import ru.twelveyes.util.JsonUtil;

import java.io.IOException;
import java.util.*;

/**
 * Created by lameroot on 26.02.14.
 * Детали услуги, такие как цена, дни занятий итп.
 *
 */
@RelationshipEntity(type = "SERVICE")
public class ServiceDetail {

    public enum WorkingDay {
        MON_FRIDAY,
        ALL_WEEK,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    @GraphId
    private Long id;
    private @StartNode Company company;
    private @EndNode Service service;
    private Double lowPrice;
    private Double highPrice;
    private String workingDays;
    @Transient
    private Set<WorkingDay> days = new HashSet<>();

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

    public ServiceDetail price(Double lowPrice, Double highPrice) {
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        return this;
    }

    public ServiceDetail addWorkingDays(WorkingDay...workingDays) {
        if ( null == workingDays ) return this;

        if ( null != this.workingDays ) {
            try {
                days = new HashSet<>(Arrays.asList(JsonUtil.toObject(WorkingDay[].class,this.workingDays)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        days.addAll(Arrays.asList(workingDays));
        this.workingDays = JsonUtil.toSafeJson(days);
        return this;
    }
}
