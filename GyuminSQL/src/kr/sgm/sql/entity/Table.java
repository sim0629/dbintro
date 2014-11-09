package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.ArrayList;

@Entity
public final class Table {
  @PrimaryKey
  private String name;
  private ArrayList<Column> columns = new ArrayList<Column>();

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public ArrayList<Column> getColumns() {
    return new ArrayList<Column>(this.columns);
  }
  public void setColumns(ArrayList<Column> columns) {
    this.columns.clear();
    this.columns.addAll(columns);
  }

  public boolean isForeignKeyNullable() {
    for(Column column : columns) {
      if(column.getIsForeignKey() && !column.getNullable())
        return false;
    }
    return true;
  }
}
