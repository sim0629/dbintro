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

  // alias가 있으면 그것,
  // 아니면 tableName이다.
  String getEffectiveName() {
    if(this.alias != null)
      return this.alias;
    return this.tableName;
  }
}
