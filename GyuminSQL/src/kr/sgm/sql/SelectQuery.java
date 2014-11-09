package kr.sgm.sql;

import java.util.ArrayList;
import java.util.HashMap;
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

  private ArrayList<ArrayList<Record>> selectedRecordsList = new ArrayList<ArrayList<Record>>();

  @Override
  void run() throws InvalidQueryException {
    selectedRecordsList.clear();
    ArrayList<Table> tables = getTables();
    checkDuplicateAlias();
    if(isSelectingAll()) prepareSelectedColumnsForAll(tables);
    ArrayList<TableAndColumnIndex> selectedColumnIndexes = getSelectedColumnIndexes(tables);
    ArrayList<ArrayList<Record>> metaRecords = getMetaRecords();
    allCombinations(tables, metaRecords, new ArrayList<Record>());

    ArrayList<ArrayList<Object>> out = new ArrayList<ArrayList<Object>>();

    ArrayList<Object> header = new ArrayList<Object>();
    for(int i = 0; i < selectedColumns.size(); i++) {
      QuerySelectedColumn qsc = selectedColumns.get(i);
      header.add(qsc.getTitle());
    }
    out.add(header);

    for(ArrayList<Record> selectedRecords : selectedRecordsList) {
      ArrayList<Object> row = new ArrayList<Object>();
      for(TableAndColumnIndex index : selectedColumnIndexes) {
        row.add(selectedRecords.get(index.ti).getValues().get(index.ci));
      }
      out.add(row);
    }

    PrettyPrinter.print(out, true);
  }

  void prepareSelectedColumnsForAll(ArrayList<Table> tables) {
    HashMap<String, Integer> nofColumnNames = new HashMap<String, Integer>();
    int i = 0;
    for(Table table : tables) {
      for(Column column : table.getColumns()) {
        String columnName = column.getName();
        Integer n = 0;
        if(nofColumnNames.containsKey(columnName))
          n = nofColumnNames.get(columnName);
        nofColumnNames.put(columnName, n + 1);
        QuerySelectedColumn qsc = new QuerySelectedColumn(referedTables.get(i).getEffectiveName(), columnName, null);
        selectedColumns.add(qsc);
      }
      i++;
    }
    for(QuerySelectedColumn qsc : selectedColumns) {
      String columnName = qsc.getColumnName();
      if(nofColumnNames.get(columnName) == 1)
        qsc.setTableName(null);
    }
  }

  ArrayList<TableAndColumnIndex> getSelectedColumnIndexes(ArrayList<Table> tables) throws InvalidQueryException {
    ArrayList<TableAndColumnIndex> indexes = new ArrayList<TableAndColumnIndex>();
    for(QuerySelectedColumn qsc : selectedColumns) {
      String tableName = qsc.getTableName();
      String columnName = qsc.getColumnName();
      boolean added = false;
      for(int i = 0; i < referedTables.size(); i++) {
        if(tableName == null || referedTables.get(i).getEffectiveName().equals(tableName)) {
          ArrayList<Column> columns = tables.get(i).getColumns();
          for(int j = 0; j < columns.size(); j++) {
            if(columns.get(j).getName().equals(columnName)) {
              if(added)
                throw new InvalidQueryException(String.format(Messages.SelectColumnResolveErrorS, columnName));
              indexes.add(new TableAndColumnIndex(i, j));
              added = true;
            }
          }
        }
      }
    }
    return indexes;
  }

  ArrayList<Table> getTables() throws InvalidQueryException {
    ArrayList<Table> tables = new ArrayList<Table>();
    for(QueryReferedTable referedTable : referedTables) {
      String tableName = referedTable.getTableName();
      Table table = infoHandler.load(tableName);
      if(table == null)
        throw new InvalidQueryException(String.format(Messages.SelectTableExistenceErrorS, tableName));
      tables.add(table);
    }
    return tables;
  }

  // 같은 effectiveName이 있으면 DuplicateAliasError를 낸다.
  // effectiveName은 QueryReferedTable.java 참조
  void checkDuplicateAlias() throws InvalidQueryException {
    ArrayList<String> names = new ArrayList<String>();
    for(QueryReferedTable referedTable : referedTables) {
      String effectiveName = referedTable.getEffectiveName();
      if(names.contains(effectiveName))
        throw new InvalidQueryException(Messages.SelectDuplicateAliasingError);
    }
  }

  void checkWhereCondition(ArrayList<Table> tables, ArrayList<Record> records) throws InvalidQueryException {
    Boolean t = true;
    if(where == null || t.equals(where.check(referedTables, tables, records))) {
      selectedRecordsList.add(records);
    }
  }

  void allCombinations(ArrayList<Table> tables, List<ArrayList<Record>> metaRecords, ArrayList<Record> combination) throws InvalidQueryException {
    if(metaRecords.size() == 0) {
      checkWhereCondition(tables, combination);
    }else {
      for(Record record : metaRecords.get(0)) {
        ArrayList<Record> newCombination = new ArrayList<Record>(combination);
        newCombination.add(record);
        allCombinations(tables, metaRecords.subList(1, metaRecords.size()), newCombination);
      }
    }
  }

  ArrayList<ArrayList<Record>> getMetaRecords() throws InvalidQueryException {
    ArrayList<ArrayList<Record>> metaRecords = new ArrayList<ArrayList<Record>>();
    for(QueryReferedTable referedTable : referedTables) {
      String tableName = referedTable.getTableName();
      DatabaseHandler<String, Record> tableHandler = DatabaseHandler.tableHandler(tableName);
      metaRecords.add(tableHandler.all());
    }
    return metaRecords;
  }
}
