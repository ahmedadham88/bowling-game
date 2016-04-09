package com.ericsson.application;

/**
 * Used in communications within the Game Class
 * @author eaedaid
 *
 */
public class GameDTO {

  private int sumOfRolls;

  private String playerName;

  private int score;

  private int strikes;

  private int pointer;

  public GameDTO(int sumOfRolls, int pointer) {
    this.sumOfRolls = sumOfRolls;
    this.pointer = pointer;
  }

  public GameDTO(String playerName, int score, int strikes) {
    this.playerName = playerName;
    this.score = score;
    this.strikes = strikes;
  }

  public int getSumOfRolls() {
    return sumOfRolls;
  }

  public void setSumOfRolls(int sumOfRolls) {
    this.sumOfRolls = sumOfRolls;
  }

  public int getPointer() {
    return pointer;
  }

  public void setPointer(int pointer) {
    this.pointer = pointer;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getStrikes() {
    return strikes;
  }

  public void setStrikes(int strikes) {
    this.strikes = strikes;
  }

}
