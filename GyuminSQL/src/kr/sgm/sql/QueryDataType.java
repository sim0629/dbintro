package kr.sgm.sql;

final class QueryDataType {
  private static final int INT = 1;
  private static final int CHAR = 2;
  private static final int DATE = 3;

  private int type;
  private int capacity;

  boolean isInt() {
    return this.type == INT;
  }

  boolean isChar() {
    return this.type == CHAR;
  }

  boolean isDate() {
    return this.type == DATE;
  }

  int getLength() {
    return this.capacity;
  }

  static QueryDataType createInt() {
    QueryDataType t = new QueryDataType(INT);
    return t;
  }

  static QueryDataType createChar(int length) {
    QueryDataType t = new QueryDataType(CHAR);
    t.capacity = length;
    return t;
  }

  static QueryDataType createDate() {
    QueryDataType t = new QueryDataType(DATE);
    return t;
  }

  private QueryDataType(int type) {
    this.type = type;
  }
}
