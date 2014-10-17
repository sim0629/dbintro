package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Persistent;

@Persistent
public final class Column {
  private String name;
  private DataType dataType;
  private boolean nullable;
  private boolean isPrimaryKey;
  private boolean isForeignKey;
  private String refTableName;
  private String refColumnName;

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public DataType getDataType() {
    return this.dataType;
  }
  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }

  public boolean getNullable() {
    return this.nullable;
  }
  public void setNullable(boolean nullable) {
    this.nullable = nullable;
  }

  public boolean getIsPrimaryKey() {
    return this.isPrimaryKey;
  }
  public void setIsPrimaryKey(boolean isPrimaryKey) {
    this.isPrimaryKey = isPrimaryKey;
  }

  public boolean getIsForeignKey() {
    return this.isForeignKey;
  }
  public void setIsForeignKey(boolean isForeignKey) {
    this.isForeignKey = isForeignKey;
  }

  public String getRefTableName() {
    return this.refTableName;
  }
  public void setRefTableName(String refTableName) {
    this.refTableName = refTableName;
  }

  public String getRefColumnName() {
    return this.refColumnName;
  }
  public void setRefColumnName(String refColumnName) {
    this.refColumnName = refColumnName;
  }
}
