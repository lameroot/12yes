package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.util.Date;
import java.util.Set;

/**
 * Created by lameroot on 28.12.13.
 */
public class Yes {

    private Integer id;
    private YesLine yesLine;
    private int number;
    private String title;
    private MediaContent avatar;
    private boolean isNotify;
    private long notifyBefore;
    private boolean isPublic;
    private boolean isCommentAllowed;
    private YesState state;
    private YesCompleted completed;
    private MediaAlbum mediaAlbum;
    private Wish wish;

    @RelatedToVia(type = "COMMENTED", direction = Direction.INCOMING)
    private Set<Comment> comments;
    private transient Date start;
    private transient Date finish;

    public Yes() {
        init();
    }

    public void init() {
        if ( null != yesLine ) {
            long timeOne = yesLine.getTime() / 12L;
            this.start = new Date(yesLine.getStartAt().getTime() + timeOne * number);
            this.finish = new Date(yesLine.getStartAt().getTime() + timeOne * (number + 1));
        }
    }

    public Date getStart() {
        return this.start;
    }

    public Date getFinish() {
        return this.finish;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public YesLine getYesLine() {
        return yesLine;
    }

    public void setYesLine(YesLine yesLine) {
        this.yesLine = yesLine;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public boolean isNotify() {
        return isNotify;
    }

    public void setNotify(boolean isNotify) {
        this.isNotify = isNotify;
    }

    public long getNotifyBefore() {
        return notifyBefore;
    }

    public void setNotifyBefore(long notifyBefore) {
        this.notifyBefore = notifyBefore;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isCommentAllowed() {
        return isCommentAllowed;
    }

    public void setCommentAllowed(boolean isCommentAllowed) {
        this.isCommentAllowed = isCommentAllowed;
    }

    public YesState getState() {
        return state;
    }

    public void setState(YesState state) {
        this.state = state;
    }

    public YesCompleted getCompleted() {
        return completed;
    }

    public void setCompleted(YesCompleted completed) {
        this.completed = completed;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public MediaAlbum getMediaAlbum() {
        return mediaAlbum;
    }

    public void setMediaAlbum(MediaAlbum mediaAlbum) {
        this.mediaAlbum = mediaAlbum;
    }

    public Wish getWish() {
        return wish;
    }

    public void setWish(Wish wish) {
        this.wish = wish;
    }

    public Comment comment(Neo4jTemplate template, Profile profile, Integer stars, String text) {
        Comment comment = template.createRelationshipBetween(profile,this,Comment.class,"COMMENTED",true);
        comment.comment(stars,text);
        return template.save(comment);
    }
}
