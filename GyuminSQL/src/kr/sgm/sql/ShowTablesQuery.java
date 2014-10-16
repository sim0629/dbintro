package kr.sgm.sql;

class ShowTablesQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "show tables";
  }
}
