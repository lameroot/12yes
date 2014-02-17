package ru.twelveyes.domain;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import ru.twelveyes.config.Neo4jConfigTest;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by lameroot on 16.02.14.
 */
public class ProfileTwitterTest extends Neo4jConfigTest {

    @Test
    @Rollback(false)
    public void testTwitter() {
        Profile profile1 = createProfile("lameroot@mail.ru","profile1");
        profileRepository.save(profile1);
        assertNotNull(profile1.getId());

        Profile profile2 = createProfile("lameroot2@mail.ru","profile2");
        profileRepository.save(profile2);
        assertNotNull(profile2.getId());

        Activity activity0 = createActivity("sport",null);
        activityRepository.save(activity0);
        assertNotNull(activity0.getId());
        Activity activity1 = createActivity("tennis",activity0);
        activityRepository.save(activity1);
        assertNotNull(activity1.getId());

        Company company1 = createCompany("school one", activity1);
        companyRepository.save(company1);
        assertNotNull(company1.getId());

        Tag tag1 = createTag("#tag1");
        Tag tag2 = createTag("tag2");
        tagRepository.save(Arrays.asList(tag1,tag2));
        assertNotNull(tag1.getId());
        assertNotNull(tag2.getId());

        Event event1 = createEvent("@@event1",company1);
        eventRepository.save(event1);
        assertNotNull(event1.getId());

        Tweet tweet = new Tweet(1L,profile1,"Profile @lameroot told us about");
        tweet.addProfileMention(profile2);
        tweet.addActivityMention(activity0).addActivityMention(activity1).addActivityMention(company1);
        tweet.addTag(tag1).addTag(tag2);
        tweet.addEventMention(event1);
        tweetRepository.save(tweet);
        assertNotNull(tweet.getId());
    }


    //@Test
    public void testCreateTweet() {
        Profile profile1 = createProfile("lameroot@mail.ru","profile1");
        profileRepository.save(profile1);
        assertNotNull(profile1.getId());

        Profile profile2 = createProfile("lameroot2@mail.ru","profile2");
        profileRepository.save(profile2);
        assertNotNull(profile2.getId());

        Profile profile3 = createProfile("lameroot3@mail.ru","profile3");
        profileRepository.save(profile3);
        assertNotNull(profile3.getId());

        profile1.addFollower(profile3);
        profileRepository.save(profile1);

        Tweet tweet = new Tweet(1L,profile1,"this is test");
        tweet.addTag(new Tag("#test"));
        tweetRepository.save(tweet);
        assertNotNull(tweet.getId());

        Tweet tweet2 = new Tweet(2L,profile1,"this is test2");
        tweet2.addTag(new Tag("#test2"));
        tweetRepository.save(tweet2);
        assertNotNull(tweet2.getId());

        Tweet tweet3 = new Tweet(3L,profile2,"this is test3");
        tweet3.addTag(new Tag("#test2"));
        tweetRepository.save(tweet3);
        assertNotNull(tweet3.getId());

        Set<Tweet> tweets = tweetRepository.findAllByProfileLogin(profile1.getLogin());
        assertNotNull(tweets);
        System.out.println("size = " + tweets.size());
        for (Tweet tweet1 : tweets) {
            System.out.println(tweet1 + " == " + tweet1.getTags());
        }
        System.out.println("found by tags");
        for (Tweet tweet1 : tweetRepository.findByTagsTag("#test2")) {
            System.out.println(tweet1);
        }

        tweet3.addProfileMention(profile3);
        tweetRepository.save(tweet3);

        System.out.println("found mentions profile");
        for (Tweet tweet1 : tweetRepository.findAll()) {
            System.out.println(tweet1 + ":" + tweet1.getProfileMentions());
        }

        tweet3.addWatchers(profile1, profile2, profile3);
        tweetRepository.save(tweet3);

        System.out.println("found watchers");
        for (Profile profile : tweet3.getWatchers()) {
            System.out.println(profile);
        }

        profile1.addFollower(profile2);
        profileRepository.save(profile1);

        System.out.println("found followers");
        for (Profile profile : profile1.getFollowers()) {
            System.out.println(profile);
        }
    }

}
