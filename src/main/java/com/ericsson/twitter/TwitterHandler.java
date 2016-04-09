package com.ericsson.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;

/**
 * This class is used to post to Twitter and it is using the Twitter4j libraries
 * @author eaedaid
 *
 */
public class TwitterHandler {

  private String consumerKey;

  private String consumerSecret;

  private String accessToken;

  private String accessTokenSecret;

  public TwitterHandler(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
    this.consumerKey = consumerKey;
    this.consumerSecret = consumerSecret;
    this.accessToken = accessToken;
    this.accessTokenSecret = accessTokenSecret;
  }

  public void post(String tweet) throws IOException, TwitterException {

    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
          .setOAuthConsumerKey(consumerKey)
          .setOAuthConsumerSecret(consumerSecret)
          .setOAuthAccessToken(accessToken)
          .setOAuthAccessTokenSecret(accessTokenSecret);

    TwitterFactory tf = new TwitterFactory(cb.build());

    Twitter twitter = tf.getInstance();

    StatusUpdate statusUpdate = new StatusUpdate(tweet);

    twitter.updateStatus(statusUpdate);

    System.out.println("Twitter updated with summary");
  }
}