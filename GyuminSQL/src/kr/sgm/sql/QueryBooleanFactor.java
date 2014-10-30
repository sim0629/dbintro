package kr.sgm.sql;

final class QueryBooleanFactor {
  private boolean not;
  private QueryBooleanTest test;

  void setNot(boolean not) {
    this.not = not;
  }

  void setTest(QueryBooleanTest test) {
    this.test = test;
  }

  boolean getNot() {
    return not;
  }

  QueryBooleanTest getTest() {
    return test;
  }
}
