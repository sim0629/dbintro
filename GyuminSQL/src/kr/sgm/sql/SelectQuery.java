package kr.sgm.sql;

import java.util.ArrayList;
import java.util.List;

import kr.sgm.sql.entity.*;

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

  @Override
  void run() throws InvalidQueryException {
    ArrayList<ArrayList<Record>> metaRecords = getMetaRecords();
    allCombinations(metaRecords, new ArrayList<Record>());
  }

  void checkWhereCondition(ArrayList<Record> records) throws InvalidQueryException {
    if(where == null || where.check(referedTables, records)) {
      // it's selected!
    }
  }

  void allCombinations(List<ArrayList<Record>> metaRecords, ArrayList<Record> combination) throws InvalidQueryException {
    if(metaRecords.size() == 0) {
      checkWhereCondition(combination);
    }else {
      for(Record record : metaRecords.get(0)) {
        ArrayList<Record> newCombination = new ArrayList<Record>(combination);
        newCombination.add(record);
        allCombinations(metaRecords.subList(1, metaRecords.size()), newCombination);
      }
    }
  }

  ArrayList<ArrayList<Record>> getMetaRecords() throws InvalidQueryException {
    ArrayList<ArrayList<Record>> metaRecords = new ArrayList<ArrayList<Record>>();
    for(QueryReferedTable referedTable : referedTables) {
      String tableName = referedTable.getTableName();
      DatabaseHandler<String, Record> tableHandler = DatabaseHandler.tableHandler(tableName);
      if(tableHandler == null)
        throw new InvalidQueryException(String.format(Messages.SelectTableExistenceErrorS, tableName));
      metaRecords.add(tableHandler.all());
    }
    return metaRecords;
  }
}
