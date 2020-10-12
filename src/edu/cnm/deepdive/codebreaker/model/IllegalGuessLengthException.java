package edu.cnm.deepdive.codebreaker.model;


/**
 *  Throws an exception upon finding illegal length in the users guess (returned from
 *  {@link edu.cnm.deepdive.codebreaker.model.Code.Guess}.
 */
public class IllegalGuessLengthException extends IllegalArgumentException {

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
