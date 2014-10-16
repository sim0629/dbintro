package kr.sgm.sql;

abstract class BaseQuery {
  abstract String getTypeString();

  void run() {
    System.out.printf(Messages.TemporarySuccessS, getTypeString());
    System.out.println("");
  }
}
