package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

abstract class QueryPredicate extends QueryBooleanTest {
  // QueryComparableOperand를 가지고 Value를 얻어오기
  protected Value resolveOperand(
    QueryComparableOperand operand,
    ArrayList<QueryReferedTable> referedTables,
    ArrayList<Table> tables,
    ArrayList<Record> records)
    throws InvalidQueryException {
    String tableName = operand.getTableName();
    if(tableName == null) {
      String columnName = operand.getColumnName();
      int theI = 0, theJ = 0;
      boolean found = false;
      for(int i = 0; i < tables.size(); i++) {
        Table table = tables.get(i);
        ArrayList<Column> columns = table.getColumns();
        for(int j = 0; j < columns.size(); j++) {
          Column column = columns.get(j);
          if(columnName.equals(column.getName())) {
            if(found)
              throw new InvalidQueryException(Messages.WhereAmbiguousReference);
            found = true;
            theI = i;
            theJ = j;
          }
        }
      }
      return records.get(theI).getValues().get(theJ);
    }else {
      int i;
      for(i = 0; i < referedTables.size(); i++) {
        String effectiveName = referedTables.get(i).getEffectiveName();
        if(operand.getTableName().equals(effectiveName))
          break;
      }
      if(i == referedTables.size())
        throw new InvalidQueryException(Messages.WhereTableNotSpecified);
      ArrayList<Column> columns = tables.get(i).getColumns();
      int j;
      for(j = 0; j < columns.size(); j++) {
        String columnName = columns.get(j).getName();
        if(operand.getColumnName().equals(columnName))
          break;
      }
      if(j == columns.size())
        throw new InvalidQueryException(Messages.WhereColumnNotExist);
      return records.get(i).getValues().get(j);
    }
  }
}
