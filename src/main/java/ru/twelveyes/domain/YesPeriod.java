package ru.twelveyes.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by lameroot on 13.01.14.
 */
//@Entity
//@Table(name = "yes_period")
public class YesPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_profile_id",nullable = false)
    private Profile profile;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "period_type")
    private PeriodType periodType;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "periods_lines", joinColumns = {
            @JoinColumn(name = "fk_period_id", nullable = false, updatable = false)
            },
            inverseJoinColumns = {
            @JoinColumn(name = "fk_line_id",nullable = false, updatable = false)
            })
    private SortedSet<YesLine> yesLines;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_background_id",nullable = true)
    private MediaContent background; //то что будет задней обложкой страницы, которую откроют по этой ссылке

    public enum PeriodType {
        YEAR,
        MONTH,
        WEEK,
        DAY,
        LAST,//последний yesLine
        LAST_COMPLETED, //последний законченный yesLine
        LIFE; //все события
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
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

    public PeriodType getPeriodType() {
        return periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public SortedSet<YesLine> getYesLines() {
        return yesLines;
    }

    public void setYesLines(SortedSet<YesLine> yesLines) {
        this.yesLines = yesLines;
    }

    public YesPeriod addYesLine(YesLine yesLine) {
        if ( null == yesLines ) yesLines = new TreeSet<YesLine>();
        yesLines.add(yesLine);
        return this;
    }

    public MediaContent getBackground() {
        return background;
    }

    public void setBackground(MediaContent background) {
        this.background = background;
    }
}
