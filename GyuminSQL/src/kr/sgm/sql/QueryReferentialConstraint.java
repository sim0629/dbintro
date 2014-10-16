package kr.sgm.sql;

import java.util.ArrayList;

final class QueryReferentialConstraint {
  private ArrayList<String> ourColumnNames = new ArrayList<String>();
  private String theirTableName;
  private ArrayList<String> theirColumnNames = new ArrayList<String>();

  QueryReferentialConstraint(ArrayList<String> ourColumnNames, String theirTableName, ArrayList<String> theirColumnNames) {
    // 넣어준 인자를 통해 밖에서 수정할 수 없게 원소를 복사
    this.ourColumnNames.addAll(ourColumnNames);
    this.theirTableName = theirTableName;
    this.theirColumnNames.addAll(theirColumnNames);
  }

  ArrayList<String> getOurColumnNames() {
    // 반환된 값을 통해 밖에서 수정할 수 없게 복사하여 반환
    return new ArrayList<String>(this.ourColumnNames);
  }

  String getTheirTableName() {
    return this.theirTableName;
  }

  ArrayList<String> getTheirColumnNames() {
    // 반환된 값을 통해 밖에서 수정할 수 없게 복사하여 반환
    return new ArrayList<String>(this.theirColumnNames);
  }
}
