package kr.sgm.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import kr.sgm.sql.entity.*;

class InsertQuery extends BaseQuery {
  @Override
  final String getTypeString() {
    return "insert";
  }

  private String tableName;
  private ArrayList<String> columnNames;
  private ArrayList<QueryComparableValue> values = new ArrayList<QueryComparableValue>();

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  void setColumnNames(ArrayList<String> columnNames) {
    this.columnNames = new ArrayList<String>(columnNames);
  }

  void addValue(QueryComparableValue value) {
    this.values.add(value);
  }

  @Override
  void run() throws InvalidQueryException {
    DatabaseHandler<String, Record> tableHandler = DatabaseHandler.tableHandler(tableName);
    tableHandler.save(makeRecord(tableHandler));
    System.out.println(Messages.InsertResult);
  }

  Record makeRecord(DatabaseHandler<String, Record> tableHandler) throws InvalidQueryException {
    Table table = infoHandler.load(tableName);
    if(table == null)
      throw new InvalidQueryException(Messages.NoSuchTable);

    ArrayList<Column> columns = table.getColumns();

    // get the order of values
    ArrayList<Integer> columnIndexes = getColumnIndexes(columns);
    if(columnIndexes.size() != values.size())
      throw new InvalidQueryException(Messages.InsertTypeMismatchError);

    // initialize values of new record
    ArrayList<Value> recordValues = new ArrayList<Value>();
    for(int i = 0; i < columns.size(); i++)
      recordValues.add(null);

    // fill values
    for(int i = 0; i < values.size(); i++) {
      QueryComparableValue qcv = values.get(i);
      int columnIndex = columnIndexes.get(i);
      Column column = columns.get(columnIndex);
      DataType dataType = column.getDataType();
      Value recordValue = new Value();
      if(dataType.isInt()) {
        if(!qcv.type.isInt())
          throw new InvalidQueryException(Messages.InsertTypeMismatchError);
        recordValue.setInt(qcv.i);
      }else if(dataType.isChar()) {
        if(!qcv.type.isChar())
          throw new InvalidQueryException(Messages.InsertTypeMismatchError);
        int len = dataType.getLength();
        String s = qcv.s;
        if(s.length() > len) s = s.substring(0, len);
        recordValue.setString(s);
      }else if(dataType.isDate()) {
        if(!qcv.type.isDate())
          throw new InvalidQueryException(Messages.InsertTypeMismatchError);
        if(qcv.d == null)
          throw new InvalidQueryException(Messages.InvalidDateRangeError);
        recordValue.setDate(qcv.d);
      }else {
        throw new IllegalStateException();
      }
      recordValues.set(columnIndex, recordValue);
    }

    // not null constraints check
    for(int i = 0; i < recordValues.size(); i++) {
      Value recordValue = recordValues.get(i);
      Column column = columns.get(i);
      if(recordValue == null && !column.getNullable())
        throw new InvalidQueryException(String.format(Messages.InsertColumnNonNullableErrorS, column.getName()));
    }

    // primary key constraint check
    ArrayList<Integer> primaryKeyIndexes = getPrimaryKeyIndexes(columns);
    for(Record record : tableHandler.all()) {
      ArrayList<Value> vs = record.getValues();
      boolean allSame = true;
      for(Integer primaryKeyIndex : primaryKeyIndexes) {
        if(!Value.same(
          columns.get(primaryKeyIndex).getDataType(),
          recordValues.get(primaryKeyIndex),
          vs.get(primaryKeyIndex))) {
          allSame = false;
          break;
        }
      }
      if(allSame)
        throw new InvalidQueryException(Messages.InsertDuplicatePrimaryKeyError);
    }

    // foreign key constraints check
    for(Entry<String, HashMap<Integer, String>> entry1 : getForeignKeysIndexes(columns).entrySet()) {
      String refTableName = entry1.getKey();
      HashMap<Integer, String> foreignKeyIndexes = entry1.getValue();
      ArrayList<Column> refTableColumns = infoHandler.load(refTableName).getColumns();
      DatabaseHandler<String, Record> refTableHandler = DatabaseHandler.tableHandler(refTableName);
      for(Record record : refTableHandler.all()) {
        ArrayList<Value> vs = record.getValues();
        boolean allSame = true;
        for(Entry<Integer, String> entry2 : foreignKeyIndexes.entrySet()) {
          Integer foreignKeyIndex = entry2.getKey();
          String refColumnName = entry2.getValue();
          int refColumnIndex = -1;
          for(int i = 0; i < refTableColumns.size(); i++) {
            if(refTableColumns.get(i).getName().equals(refColumnName)) {
              refColumnIndex = i;
              break;
            }
          }
          if(refColumnIndex < 0) throw new IllegalStateException();
          if(!Value.same(
            columns.get(foreignKeyIndex).getDataType(),
            recordValues.get(foreignKeyIndex),
            vs.get(refColumnIndex))) {
            allSame = false;
            break;
          }
        }
        if(!allSame)
          throw new InvalidQueryException(Messages.InsertReferentialIntegrityError);
      }
    }

    Record record = new Record();
    record.setUID(UUID.randomUUID().toString());
    record.setValues(recordValues);
    return record;
  }

  // returns: refTableName -> (columnIndex -> refColumnName)
  HashMap<String, HashMap<Integer, String>> getForeignKeysIndexes(ArrayList<Column> columns) {
    HashMap<String, HashMap<Integer, String>> foreignKeysIndexes = new HashMap<String, HashMap<Integer, String>>();
    for(int i = 0; i < columns.size(); i++) {
      Column column = columns.get(i);
      if(column.getIsForeignKey()) {
        String refTableName = column.getRefTableName();
        HashMap<Integer, String> foreignKeyIndexes = foreignKeysIndexes.get(refTableName);
        if(foreignKeyIndexes == null) {
          foreignKeyIndexes = new HashMap<Integer, String>();
          foreignKeysIndexes.put(refTableName, foreignKeyIndexes);
        }
        foreignKeyIndexes.put(i, column.getRefColumnName());
      }
    }
    return foreignKeysIndexes;
  }

  ArrayList<Integer> getPrimaryKeyIndexes(ArrayList<Column> columns) {
    ArrayList<Integer> primaryKeyIndexes = new ArrayList<Integer>();
    for(int i = 0; i < columns.size(); i++) {
      if(columns.get(i).getIsPrimaryKey()) {
        primaryKeyIndexes.add(i);
      }
    }
    return primaryKeyIndexes;
  }

  ArrayList<Integer> getColumnIndexes(ArrayList<Column> columns) throws InvalidQueryException {
    ArrayList<Integer> columnIndexes = new ArrayList<Integer>();

    if(columnNames == null) {
      for(int i = 0; i < columns.size(); i++)
        columnIndexes.add(i);
      return columnIndexes;
    }

    // 스펙에 따라 duplicated column name은 없다고 가정
    for(String columnName : columnNames) {
      int i;
      for(i = 0; i < columns.size(); i++) {
        if(columns.get(i).getName().equals(columnName)) {
          columnIndexes.add(i);
          break;
        }
      }
      if(i == columns.size())
        throw new InvalidQueryException(String.format(Messages.InsertColumnExistenceErrorS, columnName));
    }
    return columnIndexes;
  }
}
