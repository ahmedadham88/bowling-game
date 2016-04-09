package com.ericsson.application;

import java.io.IOException;
import java.util.Map;

import com.ericsson.twitter.TwitterHandler;

import twitter4j.TwitterException;



public class Game {

  private Map<String, Player> players;

  private GameEnvironment gameEnv;

  private TwitterHandler twitterHandler;

  private int strikeCounter;

  public Game(Map<String, Player> players, GameEnvironment gameEnv) {
    this.players = players;
    this.gameEnv = gameEnv;
    setupTwitterAPI();
  }

  private void setupTwitterAPI() {
    if (gameEnv.getTwitterAccessToken() != null && gameEnv.getTwitterAccessTokenSecret() != null
          && gameEnv.getTwitterConsumerKey() != null && gameEnv.getTwitterConsumerSecret() != null) {
      twitterHandler = new TwitterHandler(gameEnv.getTwitterConsumerKey(), gameEnv.getTwitterConsumerSecret(),
            gameEnv.getTwitterAccessToken(), gameEnv.getTwitterAccessTokenSecret());
    }
  }

  private int maxNumberOfRolls(GameEnvironment gameEnv) {
    return gameEnv.getFramesPerGame() * gameEnv.getRollsPerFrame() + 1;
  }

  /**
   * Player rolls a ball.  The score of the round is updated with the number of pins knocked down
   * @param playerName
   * @param pins
   */
  public void roll(String playerName, int pins) {
    Player player = players.get(playerName);
    int currentIndex = player.getIndex();
    if (currentIndex < maxNumberOfRolls(gameEnv)) {
      player.setRollIndex(currentIndex++, pins);
      player.setIndex(currentIndex++);
    }
  }

  /**
   * Called once at the end of the game and calculates the total score of the player
   * @param player
   * @return
   */
  public GameDTO score(String playerName) {
    int[] rolls = players.get(playerName).getRoll();
    int totalScore = 0;
    int rollTraversal = 0;
    for (int frameCounter = 0; frameCounter < gameEnv.getFramesPerGame(); frameCounter++) {
      int frameScore = 0;
      if (isStrike(rolls, rollTraversal)) {
        strikeCounter++;
        frameScore += strikeScore(rolls, rollTraversal);
        rollTraversal++;
        totalScore += frameScore;
      } else {
        GameDTO gameObject = calculateSumOfRollsInFrame(rolls, rollTraversal);
        if (isSpare(gameObject.getSumOfRolls())) {
          frameScore += spareScore(rolls, rollTraversal, gameObject.getSumOfRolls());
          rollTraversal += gameObject.getPointer();
          totalScore += frameScore;
        } else {
          frameScore += gameObject.getSumOfRolls();
          rollTraversal += gameEnv.getRollsPerFrame();
          totalScore += frameScore;
        }
      }
    }

    System.out.println("Total Game Score for player " + playerName + " is " + totalScore);
    System.out.println("Player " + playerName + " scored " + strikeCounter + " strike(s)!");

    removePlayer(playerName);

    return new GameDTO(playerName, totalScore, strikeCounter);
  }

  private boolean isStrike(int[] rolls, int rollTraversal) {
    return rolls[rollTraversal] == gameEnv.getPinsPerFrame();
  }

  private int strikeScore(int[] rolls, int rollTraversal) {
    return rolls[rollTraversal] + rolls[rollTraversal + 1] + rolls[rollTraversal + 2];
  }

  private GameDTO calculateSumOfRollsInFrame(int[] rolls, int rollTraversal) {
    int pointer = 0;
    int sumOfRolls = 0;
    for (int i = rollTraversal; i < gameEnv.getRollsPerFrame() + rollTraversal; i++) {
      sumOfRolls += rolls[i];
      pointer++;
      if (sumOfRolls == gameEnv.getPinsPerFrame()) {
        break;
      }
    }
    return new GameDTO(sumOfRolls, pointer);
  }

  private boolean isSpare(int sumOfRolls) {
    return sumOfRolls == gameEnv.getPinsPerFrame();
  }

  private int spareScore(int[] rolls, int rollTraversal, int sumOfRolls) {
    return sumOfRolls + rolls[rollTraversal + gameEnv.getRollsPerFrame()];
  }

  /**
   * Will be called after the game done to tweet about the game
   * @param playerName
   * @param totalScore
   * @param strikeCounter2
   */
  public void tweet(String playerName, int totalScore, int strikeCounter2) {
    if (twitterHandler != null) {
      try {
        String tweet = "Bowling Game: Player " + playerName + " scored " + totalScore
              + " with " + strikeCounter2 + " strike(s)!";
        twitterHandler.post(tweet);
      } catch (IOException e) {
        System.out.println("Error posting to twitter, connection error.");
      } catch (TwitterException e) {
        System.out.println("Error posting to twitter, twitter error.");
      }
    }
  }

  private void removePlayer(String playerName) {
    players.remove(playerName);
  }

  public GameEnvironment getGameEnv() {
    return gameEnv;
  }

  public void setGameEnv(GameEnvironment gameEnv) {
    this.gameEnv = gameEnv;
  }

  public int getStrikeCounter() {
    return strikeCounter;
  }

  public void setStrikeCounter(int strikeCounter) {
    this.strikeCounter = strikeCounter;
  }

  public Map<String, Player> getPlayers() {
    return players;
  }

  public void setPlayers(Map<String, Player> players) {
    this.players = players;
  }

  public TwitterHandler getTwitterHandler() {
    return twitterHandler;
  }

  public void setTwitterHandler(TwitterHandler twitterHandler) {
    this.twitterHandler = twitterHandler;
  }
}