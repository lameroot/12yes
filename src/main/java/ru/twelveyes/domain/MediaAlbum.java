package ru.twelveyes.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by lameroot on 12.01.14.
 */
public class MediaAlbum {

    private Integer id;
    private String title;
    private Date createdAt = new Date();
    private Date updatedAt;
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
