package edu.cnm.deepdive.codebreaker.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *developes a sercet code through random generation.
 */
public class Code {

  private final char[] secret;

  /**
   * Initializes this instance by generating a random {@link String}, of length {@code length} with
   * characters taken from {@code pool}. A source of randomness must be provided in {@code rng}.
   *
   * @param pool integers allowed in the  the code.
   * @param length number of characters in the code.
   * @param rng the randomization.
   */
  public Code(String pool, int length, Random rng) {
    secret = new char[length];
    for (int i = 0; i < secret.length; i++) {
      secret[i] = pool.charAt(rng.nextInt(pool.length()));
    }
  }

  @Override
  public String toString() {
    return new String(secret);
  }

  /**
   *
   */
  public class Guess {

    private static final String STRING_FORMAT = "{text: \"%s\", correct: %d, close: %d}";

    private final String text;
    private final int correct;
    private final int close;

    /**
     * Initializes this instance by computing the number of characters in the {@code text} that are
     * also in the {@link Code}, and are in the same position in both, as well as the number of
     * additional characters that are in both, but not in the same position.
     *
     * @param text Guess content.
     */
    public Guess(String text) {
      this.text = text;
      int correct = 0;
      int close = 0;

      Map<Character, Set<Integer>> letterMap = getLetterMap(text);

      char[] work = Arrays.copyOf(secret, secret.length);

      for (int i = 0; i < work.length; i++) {
        char letter = work[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
        if (positions.contains(i)) {
          correct++;
          positions.remove(i);
          work[i] = 0;
        }
      }

      for (char letter : work) {
        if (letter != 0) {
          Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
          if (positions.size() > 0) {
            close++;
            Iterator<Integer> iter = positions.iterator();
            iter.next();
            iter.remove();
          }
        }
      }

      this.correct = correct;
      this.close = close;
    }

    private Map<Character, Set<Integer>> getLetterMap(String text) {
      Map<Character, Set<Integer>> letterMap = new HashMap<>();
      char[] letters = text.toCharArray();
      for (int i = 0; i < letters.length; i++) {
        char letter = letters[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, new HashSet<>());
        positions.add(i);
        letterMap.putIfAbsent(letter, positions);
      }
      return letterMap;
    }

    @Override
    public String toString() {
      return String.format(STRING_FORMAT, text, correct, close);
    }

    /**
     * Returns the text of instance.
     *
     * @return
     */
    public String getText() {
      return text;
    }

    /**
     * Returns the characters guessed that are also in the code itself that are also in the same
     * place in the code and the guess.
     *
     */
    public int getCorrect() {
      return correct;
    }

    /**
     *  Returns the characters in the guess that are also in the code but not in the same place
     *  in both the code and the guess.
     *
     */
    public int getClose() {
      return close;
    }

  }

}
