package kr.sgm.sql;

import kr.sgm.sql.entity.Table;

abstract class BaseQuery {
  abstract String getTypeString();

  void run() throws InvalidQueryException {
    System.out.printf(Messages.TemporarySuccessS, getTypeString());
    System.out.println("");
  }

  DatabaseHandler<String, Table> infoHandler = DatabaseHandler.infoHandler();
}
