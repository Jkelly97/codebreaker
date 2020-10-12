package edu.cnm.deepdive.codebreaker.model;

import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Creates and restarts game while checking guesses for incorrect answers and throwing an exception
 * if any illegal characters are found.
 */
public class Game {

  private static final String GOOD_CHARACTER_PATTERN_FORMAT = "[%s]";
  private static final String ILLEGAL_LENGTH_MESSAGE =
      "Invalid guess length: code length is %d; guess length is %d.";
  private static final String ILLEGAL_CHARACTER_MESSAGE =
      "Guess includes invalid characters: pool is \"%s\"; guess included=\"%s\".";

  private final Code code;
  private final List<Guess> guesses;
  private final String pool;
  private final int length;
  private final String goodCharacterPattern;

  /**
   * Uses {@link Code} to make a new random secret code according to the settings set by the user.
   * Wipes all previous guesses from the list giving the user an empty guess list.
   * @param pool Valid list of characters used in the secret code.
   * @param length Amount of characters allowed in the secret code.
   * @param rng randomness incorporated in the secret code.
   */
  public Game(String pool, int length, Random rng) {
    code = new Code(pool, length, rng);
    guesses = new LinkedList<>();
    this.pool = pool;
    this.length = length;
    goodCharacterPattern = String.format(GOOD_CHARACTER_PATTERN_FORMAT, pool);
  }

  /**
   * Acquires the secret code from {@link Code}.
   */
  public Code getCode() {
    return code;
  }


  /**
   * Returns guess list.
   */
  public List<Guess> getGuesses() {
    return Collections.unmodifiableList(guesses);
  }

  /**
   * Acquires the list of usable characters from {@link Code}.
   */
  public String getPool() {
    return pool;
  }

  /**
   * Returns the amount of characters allowing the the secret code.
   */
  public int getLength() {
    return length;
  }

  /**
   * Keeps track of the number of guesses used in that game.
   */
  public int getGuessCount() {
    return guesses.size();
  }

  /**
   * Returns the users guess from {@link Guess} Checks the users guess for validity according to
   * the secret code returned from {@Code text}. Throws exceptions upon finding illegal characters
   * of invalid guess lengths.
   *
   * @param text The users guess.
   * @return
   * @throws IllegalGuessLengthException Use of illegal characters
   * @throws IllegalGuessCharacterException Use of invalid guess length.
   */
  public Guess guess(String text)
      throws IllegalGuessLengthException, IllegalGuessCharacterException {
    if (text.length() != length) {
      throw new IllegalGuessLengthException(
          String.format(ILLEGAL_LENGTH_MESSAGE, length, text.length()));
    }
    String badCharacters = text.replaceAll(goodCharacterPattern, "");
    if (!badCharacters.isEmpty()) {
      throw new IllegalGuessCharacterException(
          String.format(ILLEGAL_CHARACTER_MESSAGE, pool, badCharacters));
    }
    Guess guess = code.new Guess(text);
    guesses.add(guess);
    return guess;
  }

  /**
   * Restarts the game to a fresh guess list.
   */
  public void restart() {
    guesses.clear();
  }

}
