package kr.sgm.sql;

public class InvalidQueryException extends Exception {
  public InvalidQueryException(String message) {
    super(message);
  }
}
