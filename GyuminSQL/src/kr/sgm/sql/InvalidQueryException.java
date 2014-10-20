package kr.sgm.sql;

// ParseException은 아니지만 정상적인 쿼리가 아닐 경우
// 예외를 발생시키기 위해 사용하는 클래스이다.
// ParseException은 Parser만 내기로 한다.
public class InvalidQueryException extends Exception {
  public InvalidQueryException(String message) {
    super(message);
  }
}
