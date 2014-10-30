package kr.sgm.sql;

import java.util.ArrayList;

class SelectQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "select";
  }

  private ArrayList<QuerySelectedColumn> selectedColumns = new ArrayList<QuerySelectedColumn>();
  private ArrayList<QueryReferedTable> referedTables = new ArrayList<QueryReferedTable>();
  private QueryBooleanValueExpression where;

  void addSelectedColumn(QuerySelectedColumn selectedColumn) {
    this.selectedColumns.add(selectedColumn);
  }

  private boolean isSelectingAll() {
    // * 을 select 할 때에는
    // selectedColumns가 비어있다고 가정한다.
    return this.selectedColumns.isEmpty();
  }

  void addReferedTable(QueryReferedTable referedTable) {
    this.referedTables.add(referedTable);
  }

  void setWhere(QueryBooleanValueExpression where) {
    this.where = where;
  }
}
