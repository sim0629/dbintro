package kr.sgm.sql;

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
}
