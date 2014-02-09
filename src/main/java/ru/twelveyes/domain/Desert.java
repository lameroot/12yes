package ru.twelveyes.domain;

import java.util.Date;

/**
 * Created by lameroot on 12.01.14.
 * Заслуги, которые заслужил профиль
 */
public class Desert {

    private Integer id;
    private String title;
    private MediaContent avatar;
    private Contact contact;
    private Date createdAt = new Date();
    private Profile profile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MediaContent getAvatar() {
        return avatar;
    }

    public void setAvatar(MediaContent avatar) {
        this.avatar = avatar;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
