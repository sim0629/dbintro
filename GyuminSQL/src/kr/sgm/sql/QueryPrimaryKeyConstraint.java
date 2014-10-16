package kr.sgm.sql;

import java.util.ArrayList;

final class QueryPrimaryKeyConstraint {
  private ArrayList<String> columnNames = new ArrayList<String>();

  QueryPrimaryKeyConstraint(ArrayList<String> columnNames) {
    // 넣어준 인자를 통해 밖에서 수정할 수 없게 원소를 복사
    this.columnNames.addAll(columnNames);
  }

  ArrayList<String> getColumnNames() {
    // 반환된 값을 통해 밖에서 수정할 수 없게 복사하여 반환
    return new ArrayList<String>(this.columnNames);
  }
}
