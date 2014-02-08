package ru.twelveyes.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
@NodeEntity(partial = true)
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    private String email;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_contact_id",nullable = true)
    private Contact contact;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "profile")
    private List<Desert> deserts;
    @Transient
    @RelatedToVia(type = "RATED")
    @Fetch
    private Set<Rating> ratings;
    @RelatedToVia(type = "COMMENTED")
    @Transient
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
