package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Transient;
import org.springframework.data.neo4j.annotation.*;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
@NodeEntity
public class Profile {

    @GraphId
    private Long id;
    private Sex sex;
    private Date birthday;
    @Indexed(unique = true, indexName = "emails")
    private String email;
    @Indexed(indexName = "logins", unique = false, fieldName = "login")
    private String login;
    @Transient
    private String password;
    private Contact contact;
    private List<Desert> deserts;
    @RelatedTo(direction = Direction.OUTGOING)
    private Journal journal;
    @Fetch
    @RelatedTo(direction = Direction.OUTGOING,type = "FOLLOWED")
    private Set<Profile> followers = new HashSet<>();
    @RelatedTo(direction = Direction.BOTH, type = "BLACK_LIST")
    private Set<Profile> blackList = new HashSet<>();
    //todo: тип аользователя (это может быть просто прользователь, а может быть админ компании)
    @RelatedToVia(type = "RATED")
    @Fetch
    private Set<Rating> ratings;
    @RelatedToVia(type = "COMMENTED")
    private Set<Comment> comments;

    public Profile(){}
    public Profile(String login){
        this.login = login;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Rating rate(Neo4jTemplate template, Company company, Integer stars, String comment) {
        Rating rating = template.createRelationshipBetween(this,company,Rating.class,"RATED",false);
        rating.rate(stars,comment);
        return template.save(rating);
    }

    public Set<Profile> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Profile> followers) {
        this.followers = followers;
    }

    public Profile follow(Profile profile) {
        if ( null == profile ) return this;
        profile.addFollower(this);
        return this;
    }

    public Profile addFollower(Profile profile) {
        followers.add(profile);
        return this;
    }

    public Set<Profile> getBlackList() {
        return blackList;
    }

    public void setBlackList(Set<Profile> blackList) {
        this.blackList = blackList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (birthday != null ? !birthday.equals(profile.birthday) : profile.birthday != null) return false;
        if (email != null ? !email.equals(profile.email) : profile.email != null) return false;
        if (id != null ? !id.equals(profile.id) : profile.id != null) return false;
        if (password != null ? !password.equals(profile.password) : profile.password != null) return false;
        if (sex != profile.sex) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Profile");
        sb.append("{id=").append(id);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
