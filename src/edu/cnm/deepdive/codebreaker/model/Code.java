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
 * Generates a randomized code, of set-length from a pool of characters for the user to guess.
 */
public class Code {

  private final char[] secret;

  /**
   * Sets a random code from the pool of characters, for a specified code length, while utilizing a
   * random number generator to randomize the characters assigned to the code.
   *
   * @param pool   of acceptable characters
   * @param length the specified length of code
   * @param rng    a source of randomness
   */
  public Code(String pool, int length, Random rng) {
    secret = new char[length];
    for (int i = 0; i < length; i++) {
      secret[i] = pool.charAt(rng.nextInt(pool.length()));
    }
  }

  /**
   * @return the secret code as a string
   */
  @Override
  public String toString() {
    return new String(secret);
  }

  /**
   * Utilizes secret code created in {@link Code} and compares user guess to code. The program
   * responds with number of colors in the correct position and how colors were close (or ight, but
   * not in the proper position).
   */
  public class Guess {
    private static final String STRING_FORMAT = "{text: \"%s\", correct : %d, close: %d}";
    private final String text;
    private final int correct;
    private final int close;

    /**
     * Allows guesses to be set using the characters entered by the user.
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
     * Returns the amount of correct color guesses of this instance, in the correct position.
   */
    public int getCorrect() {
      return correct;
    }

    /**
     * Returns the amount of close guesses of this instance, proper color, but improper position.
   */
    public int getClose() {
      return close;
    }
  }
}