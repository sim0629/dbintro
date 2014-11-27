package kr.sgm.sugang;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Main {
  private static String[] menu = {
    "exit",
    "list all lectures",
    "list all students",
    "insert a lecture",
    "remove a lecture",
    "insert a student",
    "remove a student",
    "register for lecture",
    "list all lectures of a student",
    "list all registered students of a lecture"
  };
  private static String separator =
    "============================================";

  private static QueryHandler h = null;

  public static void main(String[] args) throws ClassNotFoundException {
    boolean exit = false;
    while(!exit) {
      printMenu();
      int action = scanInt("Select your action");
      try {
        if(h == null) h = new QueryHandler();
        exit = doAction(action);
      }catch(SQLException ex) {
        // Messages에 정의되지 않은 예외를 잡아서 알려준다.
        // insert 할 때
        // 중복된 primary key가 들어오거나
        // 존재하지 않는 foreign key가 들어오면
        // 발생한다.
        // 최초에 서버 접속에 실패하였을 때에도 발생한다.
        System.out.println("Unhandled SQLException");
      }
    }
  }

  private static void printMenu() {
    System.out.println(separator);
    for(int i = 1; i < menu.length; i++) {
      System.out.printf("%d. ", i);
      System.out.println(menu[i]);
    }
    System.out.printf("%d. ", menu.length);
    System.out.println(menu[0]);
    System.out.println(separator);
  }

  // action을 수행하고 종료 여부를 리턴한다.
  private static boolean doAction(int action) throws SQLException {
    int lectureId = 0;
    String studentId;
    switch(action) {
    case 1:
      h.listAllLectures();
      break;
    case 2:
      h.listAllStudents();
      break;
    case 3:
      String lectureName = scanString("Input lecture name");
      int lectureCredit = 0;
      int lectureCapacity = 0;
      while(true) {
        // credit이 0보다 크게 들어올 때까지 계속 입력 받는다.
        lectureCredit = scanInt("Input lecture credit");
        if(lectureCredit > 0) break;
        else System.out.println(Messages.INSERT_LECERR_CREDIT);
      }
      while(true) {
        // capacity가 0보다 크게 들어올 때까지 계속 입력 받는다.
        lectureCapacity = scanInt("Input lecture capacity");
        if(lectureCapacity > 0) break;
        else System.out.println(Messages.INSERT_LECERR_CAPACITY);
      }
      h.insertLecture(lectureName, lectureCredit, lectureCapacity);
      break;
    case 4:
      lectureId = scanInt("Input lecture id");
      h.removeLecture(lectureId);
      break;
    case 5:
      String studentName = scanString("Input student name");
      studentId = scanStringAndCheck("Input student id", "\\d\\d\\d\\d\\-\\d\\d\\d\\d\\d");
      h.insertStudent(studentName, studentId);
      break;
    case 6:
      studentId = scanString("Input student id");
      h.removeStudent(studentId);
      break;
    case 7:
      studentId = scanString("Input student id");
      lectureId = scanInt("Input lecture id");
      h.registerClass(studentId, lectureId);
      break;
    case 8:
      studentId = scanString("input student id");
      h.listLectures(studentId);
      break;
    case 9:
      lectureId = scanInt("Input lecture id");
      h.listStudents(lectureId);
      break;
    case 10:
      return true;
    default:
      System.out.printf(Messages.WRONG_SELECTION_DD, 1, 10);
      System.out.println();
      break;
    }
    return false;
  }

  private static int scanInt(String prompt) {
    // int로 파싱이 성공할 때까지 계속 입력 받는다.
    while(true) {
      System.out.printf("%s: ", prompt);
      @SuppressWarnings("resource")
      Scanner in = new Scanner(System.in);
      try {
        return in.nextInt();
      }catch(InputMismatchException ex) {
        System.out.println(Messages.WRONG_INPUTTYPE);
      }
    }
  }

  private static String scanString(String prompt) {
    System.out.printf("%s: ", prompt);
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);
    return in.nextLine();
  }

  private static String scanStringAndCheck(String prompt, String pattern) {
    // pattern에 맞을 때까지 계속 입력 받는다.
    while(true) {
      String s = scanString(prompt);
      if(s.matches(pattern)) return s;
      System.out.println(Messages.WRONG_INPUTTYPE);
    }
  }
}
