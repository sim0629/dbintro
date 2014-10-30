package kr.sgm.sql;

import java.util.ArrayList;

final class QueryBooleanTerm {
  private ArrayList<QueryBooleanFactor> and = new ArrayList<QueryBooleanFactor>();

  void add(QueryBooleanFactor x) {
    and.add(x);
  }

  ArrayList<QueryBooleanFactor> get() {
    return and;
  }
}
