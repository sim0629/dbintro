package kr.sgm.sql;

class DropTableQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "drop table";
  }

  private String tableName;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
