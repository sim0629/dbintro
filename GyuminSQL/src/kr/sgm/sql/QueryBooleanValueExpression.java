package kr.sgm.sql;

import java.util.ArrayList;

final class QueryBooleanValueExpression extends QueryBooleanTest {
  private ArrayList<QueryBooleanTerm> or = new ArrayList<QueryBooleanTerm>();

  void add(QueryBooleanTerm x) {
    or.add(x);
  }

  ArrayList<QueryBooleanTerm> get() {
    return or;
  }
}
