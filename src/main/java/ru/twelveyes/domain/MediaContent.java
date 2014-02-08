package ru.twelveyes.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lameroot on 28.12.13.
 */
@Entity
@Table(name = "media_content")
public class MediaContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String path;
    @Column(name = "content_type")
    private String contentType;
    private long size;
    @Column(name = "upload_at",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadAt;
    private boolean moderated;
    @Enumerated(EnumType.STRING)
    private MediaType type;
    @Enumerated(EnumType.STRING)
    private MediaContentType mediaContentType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_media_album_id", nullable = true)
    private MediaAlbum mediaAlbum;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}
