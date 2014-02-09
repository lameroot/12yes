package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
@NodeEntity
public class Profile {

    private Integer id;
    private Sex sex;
    private Date birthday;
    private String email;
    private String password;
    private Contact contact;
    private List<Desert> deserts;
    @RelatedToVia(type = "RATED")
    @Fetch
    private Set<Rating> ratings;
    @RelatedToVia(type = "COMMENTED")
    private Set<Comment> comments;

    public enum Sex {
        MALE,
        FEMALE
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Serializable getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Desert> getDeserts() {
        return deserts;
    }

    public void setDeserts(List<Desert> deserts) {
        this.deserts = deserts;
    }

    public Rating rate(Neo4jTemplate template, Company company, Integer stars, String comment) {
        Rating rating = template.createRelationshipBetween(this,company,Rating.class,"RATED",false);
        rating.rate(stars,comment);
        return template.save(rating);
    }


}
