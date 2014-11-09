package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

final class QueryBooleanValueExpression extends QueryBooleanTest {
  private ArrayList<QueryBooleanTerm> or = new ArrayList<QueryBooleanTerm>();

  void add(QueryBooleanTerm x) {
    or.add(x);
  }

  ArrayList<QueryBooleanTerm> get() {
    return or;
  }

  @Override
  public boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Record> records)
    throws InvalidQueryException {
    throw new InvalidQueryException("not implemented");
  }
}
