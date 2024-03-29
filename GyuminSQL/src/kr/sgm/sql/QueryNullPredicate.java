package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

final class QueryNullPredicate extends QueryPredicate {
  private QueryComparableOperand operand;
  private boolean not;

  void setOperand(QueryComparableOperand operand) {
    this.operand = operand;
  }

  QueryComparableOperand getOperand() {
    return this.operand;
  }

  void setNot(boolean not) {
    this.not = not;
  }

  boolean getNot() {
    return not;
  }

  @Override
  public Boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    Value value = resolveOperand(operand, referedTables, tables, records).value;
    if(not)
      return value != null;
    else
      return value == null;
  }
}
