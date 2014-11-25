package kr.sgm.sugang;

import java.sql.*;

class QueryHandler {
  private static final String SQL_LIST_ALL_LECTURES =
    "SELECT id, name, credit, capacity, " +
    "(SELECT COUNT(*) FROM registration WHERE lecture_id = id) AS current_applied " +
    "FROM lecture " +
    "ORDER BY id";

  private static final String SQL_LIST_ALL_STUDENTS =
    "SELECT student.id as id, student.name as name, " +
    "(SELECT NVL(SUM(credit), 0) " +
    " FROM registration JOIN lecture ON lecture_id = lecture.id " +
    " WHERE student_id = student.id) AS used_credits " +
    "FROM student " +
    "ORDER BY student.id";

  private static final String SEP_LECTURES =
    "----------------------------------------------------------------------";
  private static final String[] HEADER_LECTURES =
    { "id", "name", "credit", "capacity", "current_applied" };
  private static final Integer[] MAXLEN_LECTURES =
    { 10, 20, 10, 10, 15 };

  private static final String SEP_STUDENTS =
    "---------------------------------------------";
  private static final String[] HEADER_STUDENTS =
    { "id", "name", "used_credits" };
  private static final Integer[] MAXLEN_STUDENTS =
    { 10, 20, 12 };

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

  void listAllLectures() throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_LIST_ALL_LECTURES);
    ResultSet rs = ps.executeQuery();
    System.out.println(SEP_LECTURES);
    for(int i = 0; i < HEADER_LECTURES.length; i++)
      System.out.printf(String.format("%%-%ds", MAXLEN_LECTURES[i] + 1), HEADER_LECTURES[i]);
    System.out.println();
    System.out.println(SEP_LECTURES);
    while(rs.next()) {
      for(int i = 0; i < HEADER_LECTURES.length; i++) {
        Object value = rs.getObject(HEADER_LECTURES[i]);
        if(value == null) value = "null";
        System.out.printf(String.format("%%-%ds", MAXLEN_LECTURES[i] + 1), value);
      }
      System.out.println();
    }
    System.out.println(SEP_LECTURES);
  }

  void listAllStudents() throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_LIST_ALL_STUDENTS);
    ResultSet rs = ps.executeQuery();
    System.out.println(SEP_STUDENTS);
    for(int i = 0; i < HEADER_STUDENTS.length; i++)
      System.out.printf(String.format("%%-%ds", MAXLEN_STUDENTS[i] + 1), HEADER_STUDENTS[i]);
    System.out.println();
    System.out.println(SEP_STUDENTS);
    while(rs.next()) {
      for(int i = 0; i < HEADER_STUDENTS.length; i++) {
        Object value = rs.getObject(HEADER_STUDENTS[i]);
        if(value == null) value = "null";
        System.out.printf(String.format("%%-%ds", MAXLEN_STUDENTS[i] + 1), value);
      }
      System.out.println();
    }
    System.out.println(SEP_STUDENTS);
  }
}
