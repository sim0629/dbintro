package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

final class QueryComparisonPredicate extends QueryPredicate {
  private QueryComparableValue lhsValue;
  private QueryComparableOperand lhsOperand;
  private QueryComparableValue rhsValue;
  private QueryComparableOperand rhsOperand;
  private String op;

  void setLhsValue(QueryComparableValue lhsValue) {
    this.lhsValue = lhsValue;
  }

  QueryComparableValue getLhsValue() {
    return this.lhsValue;
  }

  void setLhsOperand(QueryComparableOperand lhsOperand) {
    this.lhsOperand = lhsOperand;
  }

  QueryComparableOperand getLhsOperand() {
    return this.lhsOperand;
  }

  void setRhsValue(QueryComparableValue rhsValue) {
    this.rhsValue = rhsValue;
  }

  QueryComparableValue getRhsValue() {
    return this.rhsValue;
  }

  void setRhsOperand(QueryComparableOperand rhsOperand) {
    this.rhsOperand = rhsOperand;
  }

  QueryComparableOperand getRhsOperand() {
    return this.rhsOperand;
  }

  void setOperator(String op) {
    this.op = op;
  }

  String getOperator() {
    return this.op;
  }

  @Override
  public boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Record> records)
    throws InvalidQueryException {
    throw new InvalidQueryException("not implemented");
  }
}
