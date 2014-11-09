package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

final class QueryBooleanFactor implements IWhereClause {
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

  public boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    throw new InvalidQueryException("not implemented");
  }
}
