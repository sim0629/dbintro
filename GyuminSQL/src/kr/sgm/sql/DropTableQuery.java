package kr.sgm.sql;

import java.util.UUID;

import kr.sgm.sql.entity.*;

class DropTableQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "drop table";
  }

  private String tableName;

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  @Override
  void run() throws InvalidQueryException {
    Table tableToDrop = infoHandler.load(this.tableName);
    if(tableToDrop == null)
      throw new InvalidQueryException(Messages.NoSuchTable);

    // 참조 받고 있는지 확인
    for(Table table : infoHandler.all()) {
      for(Column column : table.getColumns()) {
        if(this.tableName.equals(column.getRefTableName()))
          throw new InvalidQueryException(String.format(Messages.DropReferencedTableErrorS, this.tableName));
      }
    }

    infoHandler.remove(this.tableName);

    DatabaseHandler<UUID, Record> tableHandler = DatabaseHandler.tableHandler(this.tableName);
    tableHandler.truncate();

    System.out.printf(Messages.DropSuccessS, this.tableName);
    System.out.println("");
  }
}
