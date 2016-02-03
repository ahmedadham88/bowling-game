package com.ericsson.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.ericsson.application.Game;
import com.ericsson.application.GameDTO;
import com.ericsson.application.GameEnvironment;
import com.ericsson.application.Player;

/**
 * This class starts the bowling game
 * and runs until the game ends showing the total scores
 *
 */
public class AppStart {

  public static void main(String[] args) throws IOException {
    System.out.println("Welcome to the Bowling Game");
    System.out.println("Please Enter your name: ");

    @SuppressWarnings("resource")
    Scanner input = new Scanner(System.in);
    String name = input.nextLine();

    GameEnvironment gameEnv = new GameEnvironment();
    Map<String, Player> players = new HashMap<String, Player>();
    Player player1 = new Player(name, maxNumberOfRolls(gameEnv));
    players.put(name, player1);
    Game game = new Game(players, gameEnv);

    playGame(game, name);

    GameDTO gameScore = game.score(name);
    
    game.tweet(gameScore.getPlayerName(), gameScore.getScore(), gameScore.getStrikes());
  }

  private static int maxNumberOfRolls(GameEnvironment gameEnv) {
    return gameEnv.getFramesPerGame() * gameEnv.getRollsPerFrame() + 1;
  }

  private static void playGame(Game game, String name) {
    for (int i = 0; i < maxNumberOfRolls(game.getGameEnv()); i++) {
      int pins = randomNumberGenerator(game.getGameEnv().getPinsPerFrame());
      game.roll(name, pins);
    }
  }

  private static int randomNumberGenerator(int pinsPerFrame) {
    Random rand = new Random();
    return rand.nextInt((pinsPerFrame - 0) + 1) + 0;
  }
}
