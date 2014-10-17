package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

class DescribeQuery extends BaseQuery implements IDefinitionQuery {
  @Override
  final String getTypeString() {
    return "desc";
  }

  private String tableName;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  void run() throws InvalidQueryException {
    Table table = infoHandler.load(this.tableName);
    if(table == null)
      throw new InvalidQueryException(Messages.NoSuchTable);

    String format = "%-23s %-16s %-4s %-7s\n";
    System.out.println("-----------------------------------------------------");
    System.out.printf("table_name [%s]\n", this.tableName);
    System.out.printf(format, "column_name", "type", "null", "key");
    ArrayList<Column> columns = table.getColumns();
    for(Column column : columns) {
      String columnName = column.getName();
      if(columnName.length() > 23)
        columnName = columnName.substring(0, 20) + "...";
      String keyString;
      if(column.getIsPrimaryKey()) {
        if(column.getIsForeignKey()) keyString = "PRI,FOR";
        else keyString = "PRI";
      }else {
        if(column.getIsForeignKey()) keyString = "FOR";
        else keyString = "";
      }
      System.out.printf(format, columnName, column.getDataType(), column.getNullable() ? "Y" : "N", keyString);
    }
    System.out.println("-----------------------------------------------------");
  }
}
