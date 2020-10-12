package edu.cnm.deepdive.codebreaker.model;

/**
 * Throws an exception upon finding an illegal character in the guess acquired from
 * {@link edu.cnm.deepdive.codebreaker.model.Code.Guess}.
 */
public class IllegalGuessCharacterException extends IllegalArgumentException {

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
