package kr.sgm.sql;

import java.util.ArrayList;

class CreateTableQuery extends BaseQuery {
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
}
