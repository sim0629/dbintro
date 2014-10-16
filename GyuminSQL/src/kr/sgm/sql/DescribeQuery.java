package kr.sgm.sql;

class DescribeQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "desc";
  }

  private String tableName;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }
}
