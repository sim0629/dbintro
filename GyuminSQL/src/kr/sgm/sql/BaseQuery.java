package kr.sgm.sql;

abstract class BaseQuery {
  abstract String getTypeString();

  void run() throws InvalidQueryException {
    System.out.printf(Messages.TemporarySuccessS, getTypeString());
    System.out.println("");
  }
}
