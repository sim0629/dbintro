package kr.sgm.sql;

final class QueryComparableOperand {
  private String tableName;
  private String columnName;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  String getTableName() {
    return this.tableName;
  }

  void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  String getColumnName() {
    return this.columnName;
  }
}
