package kr.sgm.sql;

class DeleteQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "delete";
  }

  private String tableName;
  private QueryWhereClause where;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  void setWhereClause(QueryWhereClause where) {
    this.where = where;
  }
}
