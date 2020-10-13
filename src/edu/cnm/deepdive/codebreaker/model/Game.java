package edu.cnm.deepdive.codebreaker.model;

import edu.cnm.deepdive.codebreaker.model.Code.Guess;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Creates a game by utilizing {@link Game#Game(String, int, Random)} method to generate a
 * randomized code of a user-selected length from a pool of characters. Player can guess the code
 * and game will check to see if it matches.
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
   * Initializes a game using a string of a certain length with a pool of characters, it utilizes a
   * random code that's created with rng.
   *
   * @param pool   Acceptable characters to make up the code
   * @param length Acceptable length of the code
   * @param rng    a source of randomization
   */
  public Game(String pool, int length, Random rng) {
    code = new Code(pool, length, rng);
    guesses = new LinkedList<>();
    this.pool = pool;
    this.length = length;
    goodCharacterPattern = String.format(GOOD_CHARACTER_PATTERN_FORMAT, pool);
  }

  /**
   * @return the random generated code for the game.
   */
  public Code getCode() {
    return code;
  }

  /**
   * @return List of guesses that the user has already submitted.
   */
  public List<Guess> getGuesses() {
    return Collections.unmodifiableList(guesses);
  }

  /**
   * @return the pool of characters that can be used.
   */
  public String getPool() {
    return pool;
  }

  /**
   * @return the length of the code.
   */
  public int getLength() {
    return length;
  }

  /**
   * @return the number of user guesses.
   */
  public int getGuessCount() {
    return guesses.size();
  }

  /**
   * Tests user guesses against the conditions of length and illegal characters. Throws an exception
   * if user enters a guess that's too short, long, or one that doesn't use the proper characters
   * and prompts the user with message while asking for a new guess.
   *
   * @param text the user guess in string format
   * @return the player guess if it meets the above conditions for length and characters.
   * @throws IllegalGuessLengthException    if the guess length doesn't match
   * @throws IllegalGuessCharacterException if the guess characters don't match the pool
   *                                        characters.
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
   * Restarts the codebreaker game and clears previous user guesses.
   */
  public void restart() {
    guesses.clear();
  }
}