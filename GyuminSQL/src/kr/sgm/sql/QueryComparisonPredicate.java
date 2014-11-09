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
  public Boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    if(lhsValue != null && rhsValue != null)
      return compare(lhsValue, rhsValue, referedTables, tables, records);
    else if(lhsValue != null && rhsOperand != null)
      return compare(lhsValue, rhsOperand, referedTables, tables, records);
    else if(lhsOperand != null && rhsValue != null)
      return compare(lhsOperand, rhsValue, referedTables, tables, records);
    else if(lhsOperand != null && rhsOperand != null)
      return compare(lhsOperand, rhsOperand, referedTables, tables, records);
    else
      throw new IllegalStateException();
  }

  private Boolean compareFromResult(int result) {
    if(op.equals("<"))
      return result < 0;
    else if(op.equals(">"))
      return result > 0;
    else if(op.equals("="))
      return result == 0;
    else if(op.equals(">="))
      return result >= 0;
    else if(op.equals("<="))
      return result <= 0;
    else if(op.equals("!="))
      return result != 0;
    else
      throw new IllegalStateException();
  }

  private Boolean compare(QueryComparableValue lhsValue, QueryComparableValue rhsValue,
    ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    int result = 0;
    if(lhsValue.type.isInt() && rhsValue.type.isInt())
      result = Integer.compare(lhsValue.i, rhsValue.i);
    else if(lhsValue.type.isChar() && rhsValue.type.isChar())
      result = lhsValue.s.compareTo(rhsValue.s);
    else if(lhsValue.type.isDate() && rhsValue.type.isDate())
      result = lhsValue.d.compareTo(rhsValue.d);
    else
      throw new InvalidQueryException(Messages.WhereIncomparableError);
    return compareFromResult(result);
  }

  private Boolean compare(QueryComparableValue lhsValue, QueryComparableOperand rhsOperand,
    ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    Boolean swapped = compare(rhsOperand, lhsValue, referedTables, tables, records);
    if(swapped == null)
      return null;
    else
      return !swapped;
  }

  private Boolean compare(QueryComparableOperand lhsOperand, QueryComparableValue rhsValue,
    ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    ValueAndType vt = resolveOperand(lhsOperand, referedTables, tables, records);
    if(vt.value == null) return null;
    int result = 0;
    if(vt.type.isInt() && rhsValue.type.isInt())
      result = Integer.compare(vt.value.getInt(), rhsValue.i);
    else if(vt.type.isChar() && rhsValue.type.isChar())
      result = vt.value.getString().compareTo(rhsValue.s);
    else if(vt.type.isDate() && rhsValue.type.isDate())
      result = vt.value.getDate().compareTo(rhsValue.d);
    else
      throw new InvalidQueryException(Messages.WhereIncomparableError);
    return compareFromResult(result);
  }

  private Boolean compare(QueryComparableOperand lhsOperand, QueryComparableOperand rhsOperand,
    ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException {
    ValueAndType vt1 = resolveOperand(lhsOperand, referedTables, tables, records);
    ValueAndType vt2 = resolveOperand(rhsOperand, referedTables, tables, records);
    if(vt1 == null || vt2 == null) return null;
    int result = 0;
    if(vt1.type.isInt() && vt2.type.isInt())
      result = Integer.compare(vt1.value.getInt(), vt2.value.getInt());
    else if(vt1.type.isChar() && vt2.type.isChar())
      result = vt1.value.getString().compareTo(vt2.value.getString());
    else if(vt1.type.isDate() && vt2.type.isDate())
      result = vt1.value.getDate().compareTo(vt2.value.getDate());
    else
      throw new InvalidQueryException(Messages.WhereIncomparableError);
    return compareFromResult(result);
  }
}
