package kr.sgm.sql;

import kr.sgm.sql.entity.*;

class ShowTablesQuery extends BaseQuery implements IDefinitionQuery {
  @Override
  final String getTypeString() {
    return "show tables";
  }

  @Override
  void run() throws InvalidQueryException {
    System.out.println("-----------------------");
    for(Table table : infoHandler.all()) {
      String tableName = table.getName();
      if(tableName.length() > 23)
        tableName = tableName.substring(0, 20) + "...";
      System.out.printf("%-23s\n", tableName);
    }
    System.out.println("-----------------------");
  }
}
