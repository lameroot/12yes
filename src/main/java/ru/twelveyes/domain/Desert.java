package ru.twelveyes.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lameroot on 12.01.14.
 * Заслуги, которые заслужил профиль
 */
@Entity
@Table(name = "desert")
public class Desert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_avatar_id", nullable = true)
    private MediaContent avatar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_contact_id",nullable = true)
    private Contact contact;
    @Column(name = "created_at",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_profile_id",nullable = false,updatable = false)
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
