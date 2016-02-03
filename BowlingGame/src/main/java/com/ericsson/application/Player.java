package com.ericsson.application;

public class Player {

  private String name;

  private int[] roll;

  private int index;

  private int finalScore;

  public Player(String name, int maxNumberOfRolls) {
    this.name = name;
    roll = new int[maxNumberOfRolls];
    finalScore = 0;
    index = 0;
  }

  /**
   * Sets the roll array with the number of pins knocked down
   * @param index
   * @param pins
   */
  public void setRollIndex(int index, int pins) {
    roll[index] = pins;
  }

  public int[] getRoll() {
    return roll;
  }

  public void setRoll(int[] roll) {
    this.roll = roll;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getFinalScore() {
    return finalScore;
  }

  public void setFinalScore(int finalScore) {
    this.finalScore = finalScore;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
