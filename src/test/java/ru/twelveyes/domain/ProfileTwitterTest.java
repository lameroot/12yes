package ru.twelveyes.domain;

import org.junit.Test;
import ru.twelveyes.config.Neo4jConfigTest;

import java.util.Set;

/**
 * Created by lameroot on 16.02.14.
 */
public class ProfileTwitterTest extends Neo4jConfigTest {

    @Test
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
