package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

final class QueryBooleanTerm implements IWhereClause {
  private ArrayList<QueryBooleanFactor> and = new ArrayList<QueryBooleanFactor>();

  void add(QueryBooleanFactor x) {
    and.add(x);
  }

  ArrayList<QueryBooleanFactor> get() {
    return and;
  }

  public boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    throw new InvalidQueryException("not implemented");
  }
}
