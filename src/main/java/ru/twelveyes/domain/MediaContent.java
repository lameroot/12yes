package ru.twelveyes.domain;

import java.util.Date;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
public class MediaContent {

    private Integer id;
    private Object data;//данные (путь к файлу, текст итп)
    private String contentType;
    private long size;
    private Date uploadAt;
    private boolean moderated;
    private MediaType type;
    private MediaContentType mediaContentType;
    private MediaAlbum mediaAlbum;
    private Set<Tag> tags;

    public enum MediaType {
        PHOTO,
        VIDEO,
        TEXT,
        LINK;
    }

    public enum MediaContentType {
        AVATAR,
        YES_COMPLETED,//фотки, которые подтверждают, что "задание" выполненно
        YES_DURING,//фотки, которые были выполнены во время выполнения задания
        PROFILE //просто фотки пользователя
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getUploadAt() {
        return uploadAt;
    }

    public void setUploadAt(Date uploadAt) {
        this.uploadAt = uploadAt;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public MediaContentType getMediaContentType() {
        return mediaContentType;
    }

    public void setMediaContentType(MediaContentType mediaContentType) {
        this.mediaContentType = mediaContentType;
    }

    public MediaAlbum getMediaAlbum() {
        return mediaAlbum;
    }

    public void setMediaAlbum(MediaAlbum mediaAlbum) {
        this.mediaAlbum = mediaAlbum;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
