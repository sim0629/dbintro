package kr.sgm.sql;

final class QueryReferedTable {
  private String tableName;
  private String alias;

  QueryReferedTable(String tableName, String alias) {
    this.tableName = tableName;
    this.alias = alias;
  }

  String getTableName() {
    return this.tableName;
  }

  String getAlias() {
    return this.alias;
  }
}
