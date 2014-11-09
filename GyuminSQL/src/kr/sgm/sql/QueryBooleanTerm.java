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

  public Boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    Boolean r = true;
    for(QueryBooleanFactor x : and) {
      Boolean b = x.check(referedTables, tables, records);
      if(b == null) r = null;
      else if(!b) return false;
    }
    return r;
  }
}
