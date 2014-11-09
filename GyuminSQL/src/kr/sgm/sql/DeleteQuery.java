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
  }
}
