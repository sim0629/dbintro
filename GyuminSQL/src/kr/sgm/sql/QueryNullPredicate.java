package kr.sgm.sql;

final class QueryNullPredicate extends QueryPredicate {
  private QueryComparableOperand operand;

  void setOperand(QueryComparableOperand operand) {
    this.operand = operand;
  }

  QueryComparableOperand getOperand() {
    return this.operand;
  }
}
