package com.ericsson.application;

import java.util.Map;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

  private Game game;
  private GameEnvironment gameEnv;
  private Player player1;
  private Player player2;

  @Before
  public void setup() throws IOException {
    gameEnv = new GameEnvironment();
    gameEnv.setFramesPerGame(10);
    gameEnv.setPinsPerFrame(10);
    gameEnv.setPlayersPerGame(1);
    gameEnv.setRollsPerFrame(2);
    Map<String, Player> players = new HashMap<String, Player>();
    player1 = new Player("Ahmed", maxNumberOfRolls(gameEnv));
    player2 = new Player("Eric", maxNumberOfRolls(gameEnv));
    players.put(player1.getName(), player1);
    players.put(player2.getName(), player2);
    game = new Game(players, gameEnv);
  }

  private int maxNumberOfRolls(GameEnvironment gameEnv) {
    return gameEnv.getFramesPerGame() * gameEnv.getRollsPerFrame() + 1;
  }

  @Test
  public void allStrikes() {
    for (int i = 0; i < gameEnv.getFramesPerGame() + 2; i++)
      game.roll(player1.getName(), gameEnv.getPinsPerFrame());
    assertEquals(300, game.score(player1.getName()).getScore());
  }

  @Test
  public void allSpares() {
    for (int i = 0; i < gameEnv.getFramesPerGame(); i++) {
      game.roll(player1.getName(), gameEnv.getPinsPerFrame() / 2);
      game.roll(player1.getName(), gameEnv.getPinsPerFrame() / 2);
    }
    game.roll(player1.getName(), gameEnv.getPinsPerFrame() / 2);
    assertEquals(150, game.score(player1.getName()).getScore());
  }

  @Test
  public void allNormal() {
    for (int i = 0; i < gameEnv.getFramesPerGame(); i++) {
      game.roll(player1.getName(), 2);
      game.roll(player1.getName(), 3);
    }
    game.roll(player1.getName(), 5);
    assertEquals(50, game.score(player1.getName()).getScore());
  }

  @Test
  public void mixedRounds() {
    game.roll(player2.getName(), 2);
    game.roll(player2.getName(), 2);

    game.roll(player2.getName(), 10);

    game.roll(player2.getName(), 5);
    game.roll(player2.getName(), 5);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 5);
    game.roll(player2.getName(), 5);

    game.roll(player2.getName(), 10);
    game.roll(player2.getName(), 7);
    game.roll(player2.getName(), 3);

    assertEquals(107, game.score(player2.getName()).getScore());
  }

  @Test
  public void numberOfFramesNotComplete() {
    game.roll(player2.getName(), 10);

    game.roll(player2.getName(), 5);
    game.roll(player2.getName(), 5);

    game.roll(player2.getName(), 5);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);
    game.roll(player2.getName(), 3);

    game.roll(player2.getName(), 3);

    assertEquals(58, game.score(player2.getName()).getScore());
  }

  @Test
  public void fiveFramesPerGameTest() {
    gameEnv.setFramesPerGame(5);
    Map<String, Player> players = new HashMap<String, Player>();
    Player player3 = new Player("Maziar", maxNumberOfRolls(gameEnv));
    players.put(player3.getName(), player1);
    Game newGame = new Game(players, gameEnv);

    newGame.roll(player3.getName(), 5);
    newGame.roll(player3.getName(), 5);

    newGame.roll(player3.getName(), 10);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 3);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 3);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 3);

    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    gameEnv.setRollsPerFrame(2);

    assertEquals(50, newGame.score(player3.getName()).getScore());
  }

  @Test
  public void fivePinsFerFrameTest() {
    gameEnv.setPinsPerFrame(5);
    Map<String, Player> players = new HashMap<String, Player>();
    Player player3 = new Player("Daniel", maxNumberOfRolls(gameEnv));
    players.put(player3.getName(), player1);
    Game newGame = new Game(players, gameEnv);

    newGame.roll(player3.getName(), 5);

    newGame.roll(player3.getName(), 5);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 3);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 2);

    gameEnv.setRollsPerFrame(2);

    assertEquals(56, newGame.score(player3.getName()).getScore());
  }

  @Test
  public void threeRollsPerFrameTest() {
    gameEnv.setRollsPerFrame(3);
    Map<String, Player> players = new HashMap<String, Player>();
    Player player3 = new Player("Mark", maxNumberOfRolls(gameEnv));
    players.put(player3.getName(), player1);
    Game newGame = new Game(players, gameEnv);

    newGame.roll(player3.getName(), 5);
    newGame.roll(player3.getName(), 5);

    newGame.roll(player3.getName(), 10);

    newGame.roll(player3.getName(), 2);
    newGame.roll(player3.getName(), 3);
    newGame.roll(player3.getName(), 2);

    newGame.roll(player3.getName(), 3);

    gameEnv.setRollsPerFrame(2);

    assertEquals(45, newGame.score(player3.getName()).getScore());
  }
}
