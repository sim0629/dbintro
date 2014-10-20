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
    DataType t = new DataType();
    t.type = INT;
    return t;
  }

  public static DataType createChar(int length) {
    DataType t = new DataType();
    t.type = CHAR;
    t.capacity = length;
    return t;
  }

  public static DataType createDate() {
    DataType t = new DataType();
    t.type = DATE;
    return t;
  }

  // createXXX static method를 통해서만
  // 인스턴스를 만들 수 있게 제한한다.
  private DataType() {
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

  @Override
  public boolean equals(Object o) {
    if(o == this)
      return true;
    if(!(o instanceof DataType))
      return false;
    DataType dt = (DataType)o;
    return dt.type == this.type
      && dt.capacity == this.capacity;
  }

  @Override
  public int hashCode() {
    return this.capacity * 4 + this.type;
  }
}
