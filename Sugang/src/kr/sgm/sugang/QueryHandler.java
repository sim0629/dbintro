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

  private static final String SQL_INSERT_LECTURE =
    "INSERT INTO lecture VALUES ((SELECT NVL(MAX(id), 0) + 1 FROM lecture), ?, ?, ?)";

  private static final String SQL_REMOVE_LECTURE =
    "DELETE FROM lecture WHERE id = ?";

  private static final String SQL_INSERT_STUDENT =
    "INSERT INTO student VALUES (?, ?)";

  private static final String SQL_REMOVE_STUDENT =
    "DELETE FROM student WHERE id = ?";

  private static final String SQL_CHECK_STUDENT_EXIST =
    "SELECT COUNT(*) " +
    "FROM student " +
    "WHERE id = ?";

  private static final String SQL_CHECK_LECTURE_EXIST =
    "SELECT COUNT(*) " +
    "FROM lecture " +
    "WHERE id = ?";

  private static final String SQL_CHECK_CAPACITY =
    "SELECT COUNT(*) " +
    "FROM lecture " +
    "WHERE id = ? " +
    "  AND capacity > (SELECT COUNT(*) FROM registration WHERE lecture_id = id)";

  private static final String SQL_CHECK_CREDIT =
    "SELECT COUNT(*) " +
    "FROM student " +
    "WHERE student.id = ? " +
    "  AND 18 >= (SELECT NVL(SUM(credit), 0) " +
    "             FROM lecture " +
    "             WHERE lecture.id IN (SELECT lecture_id " +
    "                                  FROM registration " +
    "                                  WHERE student_id = student.id) " +
    "                OR lecture.id = ?)";

  private static final String SQL_REGISTER_CLASS =
    "INSERT INTO registration VALUES (?, ?)";

  private static final String SQL_LIST_LECTURES =
    "SELECT id, name, credit, capacity, " +
    "(SELECT COUNT(*) FROM registration WHERE lecture_id = id) AS current_applied " +
    "FROM lecture " +
    "WHERE id IN (SELECT lecture_id FROM registration WHERE student_id = ?) " +
    "ORDER BY id";

  private static final String SQL_LIST_STUDENTS =
    "SELECT id, name " +
    "FROM student " +
    "WHERE id IN (SELECT student_id FROM registration WHERE lecture_id = ?) " +
    "ORDER BY id";

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

  void insertLecture(String name, int credit, int capacity) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_INSERT_LECTURE);
    ps.setString(1, name);
    ps.setInt(2, credit);
    ps.setInt(3, capacity);
    ps.executeUpdate();
    System.out.println(Messages.INSERT_LEC_SUCCESS);
  }

  // 성공 여부 리턴
  boolean removeLecture(int id) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_REMOVE_LECTURE);
    ps.setInt(1, id);
    int n = ps.executeUpdate();
    if(n > 0) System.out.print(Messages.DELETE_LEC_SUCCESS);
    else System.out.printf(Messages.LEC_NOT_EXIST_D, id);
    System.out.println();
    return n > 0;
  }

  void insertStudent(String name, String id) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_INSERT_STUDENT);
    ps.setString(1, id);
    ps.setString(2, name);
    ps.executeUpdate();
    System.out.println(Messages.INSERT_STU_SUCCESS);
  }

  // 성공 여부 리턴
  boolean removeStudent(String id) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_REMOVE_STUDENT);
    ps.setString(1, id);
    int n = ps.executeUpdate();
    if(n > 0) System.out.print(Messages.DELETE_STU_SUCCESS);
    else System.out.printf(Messages.STU_NOT_EXIST_S, id);
    System.out.println();
    return n > 0;
  }

  // 성공 여부 리턴
  boolean registerClass(String studentId, int lectureId) throws SQLException {
    // check the student exists
    PreparedStatement ps = con.prepareStatement(SQL_CHECK_STUDENT_EXIST);
    ps.setString(1, studentId);
    ResultSet rs = ps.executeQuery();
    rs.next();
    int value = rs.getInt(1);
    if(value == 0) {
      System.out.printf(Messages.STU_NOT_EXIST_S, studentId);
      System.out.println();
      return false;
    }

    // check the lecture exists
    ps = con.prepareStatement(SQL_CHECK_LECTURE_EXIST);
    ps.setInt(1, lectureId);
    rs = ps.executeQuery();
    rs.next();
    value = rs.getInt(1);
    if(value == 0) {
      System.out.printf(Messages.LEC_NOT_EXIST_D, lectureId);
      System.out.println();
      return false;
    }

    // check the capacity
    ps = con.prepareStatement(SQL_CHECK_CAPACITY);
    ps.setInt(1, lectureId);
    rs = ps.executeQuery();
    rs.next();
    value = rs.getInt(1);
    if(value == 0) {
      System.out.println(Messages.INSERT_REGISTRERR_CAPACITY);
      return false;
    }

    // check the credit
    ps = con.prepareStatement(SQL_CHECK_CREDIT);
    ps.setString(1, studentId);
    ps.setInt(2, lectureId);
    rs = ps.executeQuery();
    rs.next();
    value = rs.getInt(1);
    if(value == 0) {
      System.out.println(Messages.INSERT_REGISTRERR_CREDIT);
      return false;
    }

    // register
    ps = con.prepareStatement(SQL_REGISTER_CLASS);
    ps.setInt(1, lectureId);
    ps.setString(2, studentId);
    ps.executeUpdate();
    System.out.println(Messages.INSERT_REGISTR_SUCCESS);
    return true;
  }

  void listLectures(String studentId) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_LIST_LECTURES);
    ps.setString(1, studentId);
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

  void listStudents(int lectureId) throws SQLException {
    PreparedStatement ps = con.prepareStatement(SQL_LIST_STUDENTS);
    ps.setInt(1, lectureId);
    ResultSet rs = ps.executeQuery();
    System.out.println(SEP_STUDENTS);
    for(int i = 0; i < HEADER_STUDENTS.length - 1; i++)
      System.out.printf(String.format("%%-%ds", MAXLEN_STUDENTS[i] + 1), HEADER_STUDENTS[i]);
    System.out.println();
    System.out.println(SEP_STUDENTS);
    while(rs.next()) {
      for(int i = 0; i < HEADER_STUDENTS.length - 1; i++) {
        Object value = rs.getObject(HEADER_STUDENTS[i]);
        if(value == null) value = "null";
        System.out.printf(String.format("%%-%ds", MAXLEN_STUDENTS[i] + 1), value);
      }
      System.out.println();
    }
    System.out.println(SEP_STUDENTS);
  }
}
