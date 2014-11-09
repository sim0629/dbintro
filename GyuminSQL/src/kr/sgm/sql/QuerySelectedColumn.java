package kr.sgm.sql;

final class QuerySelectedColumn {
  private String tableName;
  private String columnName;
  private String alias;

  QuerySelectedColumn(String tableName, String columnName, String alias) {
    this.tableName = tableName;
    this.columnName = columnName;
    this.alias = alias;
  }

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  String getTableName() {
    return this.tableName;
  }

  String getColumnName() {
    return this.columnName;
  }

  String getAlias() {
    return this.alias;
  }

  String getTitle() {
    if(alias != null) return alias;
    if(tableName == null) return columnName;
    return tableName + "." + columnName;
  }
}
