package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

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

  @Override
  void run() throws InvalidQueryException {
    Table table = infoHandler.load(tableName);
    if(table == null)
      throw new InvalidQueryException(Messages.NoSuchTable);
    ArrayList<Table> tables = new ArrayList<Table>(1);
    tables.add(table);

    QueryReferedTable qrt = new QueryReferedTable(tableName, null);
    ArrayList<QueryReferedTable> referedTables = new ArrayList<QueryReferedTable>(1);
    referedTables.add(qrt);

    ArrayList<Record> recordsToDelete = new ArrayList<Record>();

    DatabaseHandler<String, Record> tableHandler = DatabaseHandler.tableHandler(tableName);
    for(Record record : tableHandler.all()) {
      ArrayList<Record> records = new ArrayList<Record>(1);
      records.add(record);
      Boolean t = true;
      if(where == null || t.equals(where.check(referedTables, tables, records))) {
        recordsToDelete.add(record);
      }
    }

    ArrayList<Integer> primaryKeyIndexes = new ArrayList<Integer>();
    ArrayList<Column> columns = table.getColumns();
    for(int i = 0; i < columns.size(); i++) {
      Column column = columns.get(i);
      if(column.getIsPrimaryKey()) {
        primaryKeyIndexes.add(i);
      }
    }

    // find referencing tables
    ArrayList<Table> rTables = new ArrayList<Table>();
    if(primaryKeyIndexes.size() > 0) {
      for(Table otherTable : infoHandler.all()) {
        for(Column column : otherTable.getColumns()) {
          if(column.getIsForeignKey() && tableName.equals(column.getRefTableName())) {
            rTables.add(otherTable);
            break;
          }
        }
      }
    }

    int deleted = 0;
    int passed = 0;

    for(Record rtd : recordsToDelete) {
      ArrayList<ArrayList<Record>> needToUpdate = new ArrayList<ArrayList<Record>>();
      boolean donot = false;

      for(Table rTable : rTables) {
        SelectQuery select = new SelectQuery();

        QueryReferedTable referedTable = new QueryReferedTable(rTable.getName(), null);
        select.addReferedTable(referedTable);

        QueryBooleanValueExpression exp = new QueryBooleanValueExpression();
        QueryBooleanTerm term = new QueryBooleanTerm();
        for(Integer primaryKeyIndex : primaryKeyIndexes) {
          Column column = columns.get(primaryKeyIndex);
          for(Column rColumn : rTable.getColumns()) {
            if(rColumn.getIsForeignKey() && rColumn.getRefTableName().equals(tableName) && column.getName().equals(rColumn.getRefColumnName())) {
              QueryComparableOperand operand = new QueryComparableOperand();
              operand.setColumnName(rColumn.getName());

              Value value = rtd.getValues().get(primaryKeyIndex);
              QueryComparableValue qValue = null;
              if(rColumn.getDataType().isInt())
                qValue = QueryComparableValue.fromInt(value.getInt());
              else if(rColumn.getDataType().isChar())
                qValue = QueryComparableValue.fromString(value.getString());
              else if(rColumn.getDataType().isDate())
                qValue = QueryComparableValue.fromDate(value.getDate());
              else
                throw new IllegalStateException();

              QueryComparisonPredicate comp = new QueryComparisonPredicate();
              comp.setLhsOperand(operand);
              comp.setRhsValue(qValue);
              comp.setOperator("=");

              QueryBooleanFactor factor = new QueryBooleanFactor();
              factor.setTest(comp);

              term.add(factor);
            }
            break;
          }
        }
        exp.add(term);
        select.setWhere(exp);

        select.dryRun();
        if(!rTable.isForeignKeyNullable() && select.selectedRecordsList.size() > 0) {
          donot = true;
          break;
        }else {
          ArrayList<Record> selectedRecordList = new ArrayList<Record>();
          for(ArrayList<Record> selectedRecords : select.selectedRecordsList)
            selectedRecordList.add(selectedRecords.get(0));
          needToUpdate.add(selectedRecordList);
        }
      }

      if(donot) {
        passed++;
        continue;
      }

      for(int i = 0; i < rTables.size(); i++) {
        Table rTable = rTables.get(i);
        DatabaseHandler<String, Record> rTableHandler = DatabaseHandler.tableHandler(rTable.getName());
        for(Record r : needToUpdate.get(i)) {
          ArrayList<Column> rColumns = rTable.getColumns();
          for(int j = 0; j < rColumns.size(); j++) {
            Column rColumn = rColumns.get(j);
            if(rColumn.getIsForeignKey() && rColumn.getRefTableName().equals(tableName)) {
              r.setValue(j, null);
            }
          }
          rTableHandler.save(r);
        }
      }
      tableHandler.remove(rtd.getUID());
      deleted++;
    }

    System.out.println(String.format(Messages.DeleteResultD, deleted));
    if(passed > 0)
      System.out.println(String.format(Messages.DeleteReferentialIntegrityPassedD, passed));
  }
}
