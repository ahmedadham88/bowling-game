package com.ericsson.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameEnvironment {

  public static final String NUMBER_OF_ROLLS_PER_FRAME = "rolls.per.frame";
  public static final String NUMBER_OF_PINS_PER_FRAME = "pins.per.frame";
  public static final String NUMBER_OF_FRAMES_PER_GAME = "frames.per.game";
  public static final String NUMBER_OF_PLAYERS_PER_GAME = "players.per.game";

  public static final String TWITTER_CONSUMER_KEY = "consumer.key";
  public static final String TWITTER_CONSUMER_SECRET = "consumer.secret";
  public static final String TWITTER_ACCESS_TOKEN = "access.token";
  public static final String TWITTER_ACCESS_TOKEN_SECRET = "access.token.secret";

  private int rollsPerFrame;
  private int pinsPerFrame;
  private int framesPerGame;
  private int playersPerGame;

  private String twitterConsumerKey;
  private String twitterConsumerSecret;
  private String twitterAccessToken;
  private String twitterAccessTokenSecret;

  public GameEnvironment() throws IOException {
    Properties props = new Properties();
    String propsFileName = "config.properties";
    InputStream inputStream = null;

    try {
      inputStream = getClass().getClassLoader().getResourceAsStream(propsFileName);

      if (inputStream != null) {
        props.load(inputStream);
      } else {
        throw new FileNotFoundException("The file " + propsFileName + " is not found.");
      }

      String tempProperty = props.getProperty(NUMBER_OF_ROLLS_PER_FRAME);
      setRollsPerFrame((tempProperty == null) ? 2 : Integer.parseInt(tempProperty));

      tempProperty = props.getProperty(NUMBER_OF_PINS_PER_FRAME);
      setPinsPerFrame((tempProperty == null) ? 10 : Integer.parseInt(tempProperty));

      tempProperty = props.getProperty(NUMBER_OF_FRAMES_PER_GAME);
      setFramesPerGame((tempProperty == null) ? 10 : Integer.parseInt(tempProperty));

      tempProperty = props.getProperty(NUMBER_OF_PLAYERS_PER_GAME);
      setPlayersPerGame((tempProperty == null) ? 1 : Integer.parseInt(tempProperty));
      setPlayersPerGame(1);

      setTwitterConsumerKey(props.getProperty(TWITTER_CONSUMER_KEY));
      setTwitterConsumerSecret(props.getProperty(TWITTER_CONSUMER_SECRET));
      setTwitterAccessToken(props.getProperty(TWITTER_ACCESS_TOKEN));
      setTwitterAccessTokenSecret(props.getProperty(TWITTER_ACCESS_TOKEN_SECRET));

    } catch (NumberFormatException e) {
      System.out.println("Exception while getting properties due to wrong number format entered, Values will be"
            + " assigned to defaults");
      setRollsPerFrame(2);
      setPinsPerFrame(10);
      setFramesPerGame(10);
      setPlayersPerGame(1);
    } catch (Exception e) {
      System.out.println("Exception while loading the property file: " + e);
    } finally {
      inputStream.close();
    }
  }

  public int getRollsPerFrame() {
    return rollsPerFrame;
  }

  public void setRollsPerFrame(int rollsPerFrame) {
    this.rollsPerFrame = rollsPerFrame;
  }

  public int getPinsPerFrame() {
    return pinsPerFrame;
  }

  public void setPinsPerFrame(int pinsPerFrame) {
    this.pinsPerFrame = pinsPerFrame;
  }

  public int getFramesPerGame() {
    return framesPerGame;
  }

  public void setFramesPerGame(int framesPerGame) {
    this.framesPerGame = framesPerGame;
  }

  public int getPlayersPerGame() {
    return playersPerGame;
  }

  public void setPlayersPerGame(int playersPerGame) {
    this.playersPerGame = playersPerGame;
  }

  public String getTwitterConsumerKey() {
    return twitterConsumerKey;
  }

  public void setTwitterConsumerKey(String twitterConsumerKey) {
    this.twitterConsumerKey = twitterConsumerKey;
  }

  public String getTwitterConsumerSecret() {
    return twitterConsumerSecret;
  }

  public void setTwitterConsumerSecret(String twitterConsumerSecret) {
    this.twitterConsumerSecret = twitterConsumerSecret;
  }

  public String getTwitterAccessToken() {
    return twitterAccessToken;
  }

  public void setTwitterAccessToken(String twitterAccessToken) {
    this.twitterAccessToken = twitterAccessToken;
  }

  public String getTwitterAccessTokenSecret() {
    return twitterAccessTokenSecret;
  }

  public void setTwitterAccessTokenSecret(String twitterAccessTokenSecret) {
    this.twitterAccessTokenSecret = twitterAccessTokenSecret;
  }
}
