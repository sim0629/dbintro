package kr.sgm.sql;

import java.util.ArrayList;
import java.util.HashSet;

import kr.sgm.sql.entity.*;

class CreateTableQuery extends BaseQuery implements IDefinitionQuery {
  @Override
  final String getTypeString() {
    return "create table";
  }

  private String tableName;
  private ArrayList<QueryColumnDefinition> columnDefinitions = new ArrayList<QueryColumnDefinition>();
  private ArrayList<QueryPrimaryKeyConstraint> primaryKeyConstraints = new ArrayList<QueryPrimaryKeyConstraint>();
  private ArrayList<QueryReferentialConstraint> referentialConstraints = new ArrayList<QueryReferentialConstraint>();

  void setTableName(String tableName) {
    this.tableName = tableName;
  }

  void addColumnDefinition(QueryColumnDefinition columnDefinition) {
    this.columnDefinitions.add(columnDefinition);
  }

  void addPrimaryKeyConstraint(QueryPrimaryKeyConstraint primaryKeyConstraint) {
    this.primaryKeyConstraints.add(primaryKeyConstraint);
  }

  void addReferentialConstraint(QueryReferentialConstraint referentialConstraint) {
    this.referentialConstraints.add(referentialConstraint);
  }

  @Override
  void run() throws InvalidQueryException {
    infoHandler.save(makeTable());
    System.out.printf(Messages.CreateTableSuccessS, this.tableName);
    System.out.println("");
  }

  private Table makeTable() throws InvalidQueryException {
    checkTableExistence();
    Table table = new Table();
    table.setName(this.tableName);
    table.setColumns(makeColumns());
    return table;
  }

  private void checkTableExistence() throws InvalidQueryException {
    Table t = infoHandler.load(this.tableName);
    if(t != null) throw new InvalidQueryException(Messages.TableExistenceError);
  }

  private ArrayList<Column> makeColumns() throws InvalidQueryException {
    ArrayList<Column> columns = new ArrayList<Column>();
    for(QueryColumnDefinition qcd : this.columnDefinitions) {
      checkColumnDuplication(qcd.getColumnName(), columns);
      columns.add(makeColumn(qcd));
    }
    applyPrimaryKeyConstraint(columns);
    applyReferentialConstraints(columns);
    return columns;
  }

  private void checkColumnDuplication(String columnName, ArrayList<Column> columns) throws InvalidQueryException {
    for(int i = 0; i < columns.size(); i++) {
      if(columnName.equals(columns.get(i).getName()))
        throw new InvalidQueryException(Messages.DuplicateColumnDefError);
    }
  }

  private Column makeColumn(QueryColumnDefinition qcd) throws InvalidQueryException {
    Column column = new Column();
    column.setName(qcd.getColumnName());
    column.setDataType(makeDataType(qcd.getDataType()));
    column.setNullable(qcd.getNullable());
    return column;
  }

  private DataType makeDataType(QueryDataType qdt) throws InvalidQueryException {
    if(qdt.isInt()) {
      return DataType.createInt();
    }else if(qdt.isChar()) {
      int length = qdt.getLength();
      if(length < 1)
        throw new InvalidQueryException(Messages.CharLengthError);
      return DataType.createChar(length);
    }else if(qdt.isDate()) {
      return DataType.createDate();
    }
    throw new IllegalStateException();
  }

  private void applyPrimaryKeyConstraint(ArrayList<Column> columns) throws InvalidQueryException {
    if(this.primaryKeyConstraints.size() > 1)
      throw new InvalidQueryException(Messages.DuplicatePrimaryKeyDefError);
    for(QueryPrimaryKeyConstraint qpkc : this.primaryKeyConstraints) {
      ArrayList<String> columnNames = qpkc.getColumnNames();
      checkUniquenessOfColumnNames(columnNames);
      for(String columnName : columnNames) {
        setColumnAsPrimaryKey(columnName, columns);
      }
    }
  }

  private void setColumnAsPrimaryKey(String columnName, ArrayList<Column> columns) throws InvalidQueryException {
    for(Column column : columns) {
      if(column.getName().equals(columnName)) {
        column.setIsPrimaryKey(true);
        // primary key는 자동으로 not null
        column.setNullable(false);
        return;
      }
    }
    throw new InvalidQueryException(String.format(Messages.NonExistingColumnDefErrorS, columnName));
  }

  private void applyReferentialConstraints(ArrayList<Column> columns) throws InvalidQueryException {
    for(QueryReferentialConstraint qrc : this.referentialConstraints) {
      Table theirTable = infoHandler.load(qrc.getTheirTableName());
      // 참조 테이블 존재 확인
      if(theirTable == null)
        throw new InvalidQueryException(Messages.ReferenceTableExistenceError);
      ArrayList<String> ourColumnNames = qrc.getOurColumnNames();
      checkUniquenessOfColumnNames(ourColumnNames);
      ArrayList<String> theirColumnNames = qrc.getTheirColumnNames();
      checkUniquenessOfColumnNames(theirColumnNames);
      // 참조 하는 쪽과 받는 쪽의 컬럼 개수가 다르면
      // ReferenceTypeError를 내기로 한다.
      if(ourColumnNames.size() != theirColumnNames.size())
        throw new InvalidQueryException(Messages.ReferenceTypeError);
      // foreign key 설정
      //  참조 받는 테이블의 primary key(tuple)에 대해
      //  참조 받는 쪽 컬럼 집합에 있는지 확인하면서
      //  그에 맞는 참조 하는 쪽 컬럼에
      //  외래키 설정을 한다.
      HashSet<String> theirColumnNamesSet = new HashSet<String>(theirColumnNames);
      for(Column theirColumn : theirTable.getColumns()) {
        if(theirColumn.getIsPrimaryKey()) {
          int nth = theirColumnNames.indexOf(theirColumn.getName());
          if(nth < 0)
            throw new InvalidQueryException(Messages.ReferenceNonPrimaryKeyError);
          setColumnAsForeignKey(ourColumnNames.get(nth), columns, theirTable.getName(), theirColumn);
          theirColumnNamesSet.remove(theirColumn.getName());
        }
      }
      if(theirColumnNamesSet.size() > 0)
        throw new InvalidQueryException(String.format(Messages.NonExistingColumnDefErrorS, theirColumnNamesSet.toArray()[0]));
    }
  }

  private void setColumnAsForeignKey(String ourColumnName, ArrayList<Column> ourColumns, String theirTableName, Column theirColumn) throws InvalidQueryException {
    for(Column ourColumn : ourColumns) {
      if(ourColumn.getName().equals(ourColumnName)) {
        if(!ourColumn.getDataType().equals(theirColumn.getDataType()))
          throw new InvalidQueryException(Messages.ReferenceTypeError);
        ourColumn.setIsForeignKey(true);
        ourColumn.setRefTableName(theirTableName);
        ourColumn.setRefColumnName(theirColumn.getName());
        return;
      }
    }
    throw new InvalidQueryException(String.format(Messages.NonExistingColumnDefErrorS, ourColumnName));
  }

  private void checkUniquenessOfColumnNames(ArrayList<String> columnNames) throws InvalidQueryException {
    // composite key를 구성하는 컬럼 이름 중에
    // 중복되는 것이 있으면 예외를 낸다.
    // primary key 뿐만 아니라
    // foreign key의 참조 하는 쪽과 받는 쪽에 나타나는
    // 컬럼 목록 확인에도 사용한다.
    HashSet<String> set = new HashSet<String>();
    for(String columnName : columnNames) {
      if(!set.add(columnName))
        throw new InvalidQueryException(Messages.DuplicateColumnDefError);
    }
  }
}
