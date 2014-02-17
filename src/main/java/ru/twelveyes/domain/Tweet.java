package ru.twelveyes.domain;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lameroot on 16.02.14.
 */
@NodeEntity
public class Tweet {

    @GraphId
    private Long id;
    @Indexed(unique = true)
    private Long tweetId;
    private String text;
    @Fetch
    @RelatedTo(type = "POSTED", direction = Direction.INCOMING)
    private Profile sender;
//    @Fetch
    @RelatedTo(type = "TAG")
    private Set<Tag> tags = new HashSet<>();
//    @Fetch
    @RelatedTo(type = "ACTIVITY_MENTION")
    private Set<Activity> activityMentions = new HashSet<>();
//    @Fetch
    @RelatedTo(type = "PROFILE_MENTION")
    private Set<Profile> profileMentions = new HashSet<>();
    @RelatedTo(type = "EVENT_MENTION")
    private Set<Event> eventMentions = new HashSet<>();
    @Fetch
    @RelatedTo(type = "SOURCE_TWEET")
    private Tweet source;
    @RelatedTo(type = "WATCHERS")
    private Set<Profile> watchers = new HashSet<>();

    private boolean isPrivate;

    public Tweet() {
    }

    public Tweet(long tweetId, Profile sender, String text) {
        this.tweetId = tweetId;
        this.sender = sender;
        this.text = text;
    }

    public Tweet addActivityMention(Activity activity) {
        this.activityMentions.add(activity);
        return this;
    }

    public Tweet addProfileMention(Profile profile) {
        this.profileMentions.add(profile);
        return this;
    }

    public Tweet addEventMention(Event event) {
        this.eventMentions.add(event);
        return this;
    }

    public Tweet addTag(Tag tag) {
        tags.add(tag);
        return this;
    }

    public Tweet addWatcher(Profile profile) {
        if ( profile.equals(sender) ) return this;
        watchers.add(profile);
        return this;
    }
    public Tweet addWatchers(Profile... profiles) {
        for (Profile profile : profiles) {
            addWatcher(profile);
        }
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTweetId() {
        return tweetId;
    }

    public void setTweetId(Long tweetId) {
        this.tweetId = tweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Activity> getActivityMentions() {
        return activityMentions;
    }

    public void setActivityMentions(Set<Activity> activityMentions) {
        this.activityMentions = activityMentions;
    }

    public Tweet getSource() {
        return source;
    }

    public void setSource(Tweet source) {
        this.source = source;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Set<Profile> getProfileMentions() {
        return profileMentions;
    }

    public void setProfileMentions(Set<Profile> profileMentions) {
        this.profileMentions = profileMentions;
    }

    public Set<Profile> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<Profile> watchers) {
        this.watchers = watchers;
    }

    @Override
    public String toString() {
        return "Tweet " + tweetId +
                ": " + text  +
                " by " + sender;
    }

}
