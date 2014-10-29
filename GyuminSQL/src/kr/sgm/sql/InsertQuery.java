package kr.sgm.sql;

import java.util.ArrayList;

class InsertQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "insert";
  }

  private String tableName;
  private ArrayList<String> columnNames;
  private ArrayList<QueryComparableValue> values = new ArrayList<QueryComparableValue>();

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  void setColumnNames(ArrayList<String> columnNames) {
    this.columnNames = new ArrayList<String>(columnNames);
  }

  void addValue(QueryComparableValue value) {
    this.values.add(value);
  }
}
