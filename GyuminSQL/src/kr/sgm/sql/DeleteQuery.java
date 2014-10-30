package kr.sgm.sql;

class DeleteQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "delete";
  }

  private String tableName;
  private QueryBooleanValueExpression where;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  void setWhere(QueryBooleanValueExpression where) {
    this.where = where;
  }
}
