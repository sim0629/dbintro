package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Persistent;

@Persistent
public final class DataType {
  private static final int INT = 1;
  private static final int CHAR = 2;
  private static final int DATE = 3;

  private int type;
  private int capacity;

  public static DataType createInt() {
    DataType t = new DataType(INT);
    return t;
  }

  public static DataType createChar(int length) {
    DataType t = new DataType(CHAR);
    t.capacity = length;
    return t;
  }

  public static DataType createDate() {
    DataType t = new DataType(DATE);
    return t;
  }

  private DataType(int type) {
    this.type = type;
  }

  @Override
  public String toString() {
    if(this.type == INT)
      return "int";
    else if(this.type == CHAR)
      return String.format("char(%d)", this.capacity);
    else if(this.type == DATE)
      return "date";
    else
      throw new IllegalStateException();
  }
}
