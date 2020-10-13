package edu.cnm.deepdive.codebreaker.model;

/**
 * An exception class that extends {@link IllegalArgumentException} for the incorrect guess length
 */

public class IllegalGuessLengthException extends IllegalArgumentException {

  /**
   * Different overloaded methods that return a message, the cause, or both of an IllegalGuessLength
   * exception and handle the exception.
   */
  public IllegalGuessLengthException() {
  }

  public IllegalGuessLengthException(String message) {
    super(message);
  }

  public IllegalGuessLengthException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalGuessLengthException(Throwable cause) {
    super(cause);
  }


}
