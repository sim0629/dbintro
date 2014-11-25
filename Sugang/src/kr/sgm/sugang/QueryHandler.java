package kr.sgm.sugang;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class QueryHandler {
  Connection con;

  QueryHandler() throws ClassNotFoundException, SQLException {
    // ConnectionInfo.java.example을 복사하여
    // ConnectionInfo.java를 만들고, 알맞게 수정해야 함
    Class.forName(ConnectionInfo.DRIVER_NAME);
    con = DriverManager.getConnection(
      ConnectionInfo.URL,
      ConnectionInfo.USER,
      ConnectionInfo.PASS
    );
  }
}
