package edu.cnm.deepdive.codebreaker.controller;

import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import edu.cnm.deepdive.codebreaker.model.Game;
import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implements a simple console-mode game in which the program generates a secret code and the user
 * guesses the code. After each guess, the program displays the results of that guess&mdash;namely,
 * the number of characters in the correct position, and the number of additional characters that
 * are in the secret code and the guess, but are not in the correct position in the guess.
 *
 * @version 1.0
 * @author Justin Kelly
 */
public class Codebreaker {

  private static final String PROLOGUE_FORMAT = "Pool: %s. Code length: %d%n";
  private static final String GUESS_PROMPT_FORMAT = "Guess #%d: ";
  private static final String GUESS_RESULT_FORMAT = "In position: %d. Out of position: %d.%n";
  private static final String SUCCESS_MESSAGE_FORMAT =
      "Correct! Secret code (\"%s\") found in %d guesses.%n";
  private static final String NO_INPUT_MESSAGE = "Unable to read input. Program stopped.";
  private static final String CHARACTER_POOL = "ROYGBIV";
  private static final int CODE_LENGTH = 4;

  /**
   *Entry point for Codebreaker game.  Creates instance of {@link Game} class, and reapeatedly takes
   * user  inpit to submit as a guess, using the {@link Game#guess(String)} method.
   *
   * @param args Command-line arguments (ignored)
   */
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      Game game = new Game(CHARACTER_POOL, CODE_LENGTH, new SecureRandom());
      System.out.printf(PROLOGUE_FORMAT, game.getPool(), game.getLength());
      //noinspection StatementWithEmptyBody
      while (!solved(scanner, game)) {}
    } catch (NoSuchElementException | IllegalStateException e) {
      System.out.println(NO_INPUT_MESSAGE);
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static boolean solved(Scanner scanner, Game game) {
    boolean correct = false;
    try {
      System.out.printf(GUESS_PROMPT_FORMAT, game.getGuessCount() + 1);
      String text = scanner.nextLine().trim().toUpperCase();
      Guess guess = game.guess(text);
      if (guess.getCorrect() == game.getLength()) {
        System.out.printf(SUCCESS_MESSAGE_FORMAT, game.getCode(), game.getGuessCount());
        correct = true;
      } else {
        System.out.printf(GUESS_RESULT_FORMAT, guess.getCorrect(), guess.getClose());
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
    return correct;
  }

}
