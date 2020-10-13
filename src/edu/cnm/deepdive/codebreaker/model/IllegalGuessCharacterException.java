package edu.cnm.deepdive.codebreaker.model;

/**
 * An exception class that extends {@link IllegalArgumentException} for the incorrect use of pool of
 * characters.
 */
public class IllegalGuessCharacterException extends IllegalArgumentException {

  /**
   * Different overloaded methods that return a message, the cause, or both of an
   * IllegalGuessCharacter exception and handle the exception.
   */
  public IllegalGuessCharacterException() {
  }

  public IllegalGuessCharacterException(String message) {
    super(message);
  }

  public IllegalGuessCharacterException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalGuessCharacterException(Throwable cause) {
    super(cause);
  }
}
