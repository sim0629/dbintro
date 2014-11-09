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
  public boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Record> records)
    throws InvalidQueryException {
    throw new InvalidQueryException("not implemented");
  }
}
