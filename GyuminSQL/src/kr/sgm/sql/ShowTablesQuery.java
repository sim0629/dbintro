package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

class ShowTablesQuery extends BaseQuery implements IDefinitionQuery {
  @Override
  final String getTypeString() {
    return "show tables";
  }

  @Override
  void run() throws InvalidQueryException {
    ArrayList<Table> tables = infoHandler.all();
    if(tables.size() == 0) {
      System.out.println(Messages.ShowTablesNoTable);
      return;
    }

    ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>(tables.size());
    for(Table table : tables) {
      ArrayList<Object> row = new ArrayList<Object>(1);
      String tableName = table.getName();
      row.add(tableName);
      rows.add(row);
    }

    PrettyPrinter.print(rows, false);
  }
}
