package kr.sgm.sql;

import java.util.ArrayList;
import java.util.Arrays;

import kr.sgm.sql.entity.*;

class DescribeQuery extends BaseQuery {
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

    System.out.printf("table_name [%s]\n", this.tableName);
    ArrayList<ArrayList<Object>> out = new ArrayList<ArrayList<Object>>();
    out.add(new ArrayList<Object>(Arrays.asList(
      "column_name",
      "type",
      "null",
      "key")));
    ArrayList<Column> columns = table.getColumns();
    for(Column column : columns) {
      String columnName = column.getName();
      String keyString;
      if(column.getIsPrimaryKey()) {
        if(column.getIsForeignKey()) keyString = "PRI,FOR";
        else keyString = "PRI";
      }else {
        if(column.getIsForeignKey()) keyString = "FOR";
        else keyString = "";
      }
      out.add(new ArrayList<Object>(Arrays.asList(
        columnName,
        column.getDataType(),
        column.getNullable() ? "Y" : "N",
        keyString)));
    }

    PrettyPrinter.print(out, true);
  }
}
