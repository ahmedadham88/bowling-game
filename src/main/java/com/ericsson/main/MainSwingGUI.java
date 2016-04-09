package com.ericsson.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ericsson.application.Game;
import com.ericsson.application.GameDTO;
import com.ericsson.application.GameEnvironment;
import com.ericsson.application.Player;

/**
 * By Running this class, you can get the GUI to interact with the Bowling Game
 * After entering your name you can roll or get the score at the end
 * @author eaedaid
 *
 */
public class MainSwingGUI extends JFrame implements ActionListener {

  private JLabel label;
  private JTextField textField;
  private JButton playButton;
  private JButton rollButton;
  private JButton scoreButton;
  private JTextArea textArea;

  private String playerName;
  private int rollCounter;
  private String finalResult;

  private Game game;
  private GameEnvironment gameEnv;

  public MainSwingGUI() {
    this.getContentPane().setLayout(new FlowLayout());

    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(250, 250));

    label = new JLabel("Bowling Game", JLabel.CENTER);
    Font labelFont = label.getFont();
    label.setFont(new Font(labelFont.getName(), Font.BOLD, 24));

    textField = new JTextField("Enter Name");
    textField.setFont(new Font(labelFont.getName(), Font.PLAIN, 18));

    playButton = new JButton("Play");
    rollButton = new JButton("Roll");
    scoreButton = new JButton("Final Score");
    textArea = new JTextArea("Roll to Start the Game");
    textArea.setFont(new Font(labelFont.getName(), Font.PLAIN, 18));

    rollButton.setVisible(false);
    scoreButton.setVisible(false);
    textArea.setVisible(false);

    rollButton.addActionListener(this);
    playButton.addActionListener(this);
    scoreButton.addActionListener(this);

    add(panel, BorderLayout.CENTER);
    panel.add(label, BorderLayout.NORTH);
    panel.add(textField, BorderLayout.CENTER);
    panel.add(playButton, BorderLayout.SOUTH);
    panel.add(rollButton, BorderLayout.NORTH);
    panel.add(scoreButton, BorderLayout.SOUTH);
    panel.add(textArea, BorderLayout.CENTER);
  }

  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createFrame();
      }
    });
  }

  public static void createFrame() {
    JFrame mainFrame = new MainSwingGUI();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setSize(250, 250);
    mainFrame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    if (action.equals("Play")) {
      doPlayAction();
    } else if (action.equals("Roll")) {
      doRollAction();
    } else if (action.equals("Final Score")) {
      doFinalScoreAction();
    }
  }

  private void doPlayAction() {
    playerName = textField.getText();
    try {
      initializeGame(playerName);
    } catch (IOException e1) {
      System.out.println("Error initializing the game");
    }
    rollButton.setVisible(true);
    scoreButton.setVisible(true);
    playButton.setVisible(false);
    textField.setVisible(false);
    textArea.setVisible(true);
    label.setVisible(false);
  }

  private void initializeGame(String playerName) throws IOException {
    gameEnv = new GameEnvironment();
    Map<String, Player> players = new HashMap<String, Player>();
    Player player1 = new Player(playerName, maxNumberOfRolls(gameEnv));
    players.put(playerName, player1);
    game = new Game(players, gameEnv);
  }

  private int maxNumberOfRolls(GameEnvironment gameEnv) {
    return gameEnv.getFramesPerGame() * gameEnv.getRollsPerFrame() + 1;
  }

  private void doRollAction() {
    rollCounter++;
    textArea.setText("You played " + rollCounter + " times");
    game.roll(playerName, randomNumberGenerator(gameEnv.getPinsPerFrame()));
  }

  private static int randomNumberGenerator(int pinsPerFrame) {
    Random rand = new Random();
    return rand.nextInt((pinsPerFrame - 0) + 1) + 0;
  }

  private void doFinalScoreAction() {
    rollButton.setVisible(false);
    scoreButton.setVisible(false);
    GameDTO gameScore = game.score(playerName);
    textArea.setText("The Bowling Game is Over " + "\n" + " Player " + playerName + "\n" + " scored " + gameScore.getScore());
    game.tweet(gameScore.getPlayerName(), gameScore.getScore(), gameScore.getStrikes());
  }
}