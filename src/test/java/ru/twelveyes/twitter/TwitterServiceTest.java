package ru.twelveyes.twitter;

import org.eclipse.jetty.server.Authentication;
import org.junit.Test;
import org.springframework.social.twitter.api.SearchOperations;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.twelveyes.AbstractConfigTest;
import ru.twelveyes.config.Neo4jConfigTest;
import ru.twelveyes.domain.Profile;
import ru.twelveyes.domain.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lameroot on 16.02.14.
 */
@Transactional
public class TwitterServiceTest extends Neo4jConfigTest{



    public List<Tweet> importTweets(String search, Long lastTweetId) {
        System.out.println("Importing for " +search+ ", max tweet id: "+lastTweetId);

        final SearchOperations searchOperations = new TwitterTemplate().searchOperations();

        final SearchResults results = lastTweetId==null ? searchOperations.search(search,1,lastTweetId,200)
                : searchOperations.search(search,1,200L,Long.MAX_VALUE);

        final List<Tweet> result = new ArrayList<Tweet>();
        for (org.springframework.social.twitter.api.Tweet tweet : results.getTweets()) {
            result.add(importTweet(tweet));
        }
        return result;
    }

    @Transactional
    protected Tweet importTweet(org.springframework.social.twitter.api.Tweet source) {
        final String userName = source.getFromUser();
        System.out.println("userName : " + userName);
        Profile user = profileRepository.save(new Profile(userName));
        final String text = source.getText();
        System.out.println("text : " + text);
        final Tweet tweet = new Tweet(source.getId(), user, text);
        System.out.println("Imported " + tweet);
//        addMentions(tweet, text);
//        addTags(tweet, text);
//        addOriginalTweet(tweet, source.getInReplyToStatusId());
        return tweetRepository.save(tweet);
    }


    @Test
    @Transactional
    public void testImportSimpleTweet() throws Exception {
        final org.springframework.social.twitter.api.Tweet source =
                new org.springframework.social.twitter.api.Tweet(123L, "Text", null, "springsource", null, null, 234L, null, null);
        System.out.println("source = " + source);
        final Tweet tweet = importTweet(source);
        assertNotNull(tweet.getId());
        assertEquals((Long)123L,tweet.getTweetId());
//        final Authentication.User sender = tweet.getSender();
//        assertNotNull(sender.getId());
//        assertEquals("springsource", sender.getUser());
    }
}
