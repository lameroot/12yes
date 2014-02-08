package ru.twelveyes.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by lameroot on 12.01.14.
 */
@Entity
@Table(name = "media_album")
public class MediaAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    @Column(name = "created_at",nullable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    @Column(name = "updated_at",nullable = true,updatable = true,insertable = false)
    private Date updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mediaAlbum")
    private List<MediaContent> contents;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<MediaContent> getContents() {
        return contents;
    }

    public void setContents(List<MediaContent> contents) {
        this.contents = contents;
    }
}
