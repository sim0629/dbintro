package kr.sgm.sql;

import java.util.Date;

final class QueryComparableValue {
  QueryDataType type;
  int i;
  String s;
  Date d;

  private QueryComparableValue() {
  }

  static QueryComparableValue fromInt(int i) {
    QueryComparableValue v = new QueryComparableValue();
    v.type = QueryDataType.createInt();
    v.i = i;
    return v;
  }

  static QueryComparableValue fromString(String s) {
    QueryComparableValue v = new QueryComparableValue();
    v.type = QueryDataType.createChar(0); // length는 의미없는 값
    v.s = s;
    return v;
  }

  static QueryComparableValue fromDate(Date d) {
    QueryComparableValue v = new QueryComparableValue();
    v.type = QueryDataType.createDate();
    v.d = d;
    return v;
  }
}
