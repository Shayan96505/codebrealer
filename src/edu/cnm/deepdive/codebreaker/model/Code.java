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
 * Generates a randomized code, of set-length for the user to guess such that
 */
public class Code {

  private final char[] secret;

  /**
   * Sets a random code from the pool of characters, for a specified code length, while utilizing
   * a random number generator randomize the pool of characters assigned to the pool.
   *
   * @param pool
   * @param length
   * @param rng
   */
  public Code(String pool, int length, Random rng) {
    secret = new char[length];
    for (int i = 0; i < length; i++) {
      secret[i] = pool.charAt(rng.nextInt(pool.length()));
    }
  }

  @Override
  public String toString() {
    return new String(secret);
  }

/**
 * Allows the user to guess UpperCase characters from the ROYGBIV pool for the codebreaker game
 */
  public class Guess {
    private static final String STRING_FORMAT = "{text: \"%s\", correct : %d, close: %d}";
    private final String text;
    private final int correct;
    private final int close;

  /**
   * Allows guesses to be set using the letters entered by the user.
   * @param text refers to text input by user into the GUI
   */
    public Guess(String text) {
      this.text = text;
      int correct = 0;
      int close = 0;

      Map<Character, Set<Integer>> letterMap = getLetterMap(text);

      char[] work = Arrays.copyOf(secret, secret.length);
      for( int i = 0; i < work.length; i++) {
        char letter = work[i];
        Set<Integer> positions = letterMap.getOrDefault(letter, Collections.emptySet());
        if(positions.contains(i)) {
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
      char [] letters = text.toCharArray();
      for(int i = 0; i < letters.length; i++) {
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
   * Returns the text of the this instance.
   */
    public String getText() {
      return text;
    }

  /**
   * Returns the amount of correct guesses of this instance.
   */
    public int getCorrect() {
      return correct;
    }

  /**
   * Returns the amount of close guess of this instance.
   */
    public int getClose() {
      return close;
    }
  }
}