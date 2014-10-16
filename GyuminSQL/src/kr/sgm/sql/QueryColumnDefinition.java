package kr.sgm.sql;

final class QueryColumnDefinition {
  private String columnName;
  private QueryDataType dataType;
  private boolean nullable;

  QueryColumnDefinition(String columnName, QueryDataType dataType, boolean nullable) {
    this.columnName = columnName;
    this.dataType = dataType;
    this.nullable = nullable;
  }

  String getColumnName() {
    return this.columnName;
  }

  QueryDataType getDataType() {
    return this.dataType;
  }

  boolean getNullable() {
    return this.nullable;
  }
}
