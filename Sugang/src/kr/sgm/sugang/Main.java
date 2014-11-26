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
      // action은 int가 아닐 수도 있기 때문에
      // action이 int로 파싱되지 않는 것은
      // WRONG_INPUTTYPE이 아니다.
      // 기본값을 0으로 주고 WRONG_SELECTION으로 처리되게 한다.
      int action = tryScanInt("Select your action", 0);
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
    boolean wrong = false;
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
      // 일단 모든 input을 받고
      // credit과 capacity 중 하나라도 int로 파싱되지 않으면
      // WRONG_INPUTTYPE을 한 번만 출력하고
      // insert는 시도하지 않는다.
      int lectureCredit = 0;
      int lectureCapacity = 0;
      String lectureName = scanString("Input lecture name");
      try { lectureCredit = scanInt("Input lecture credit"); }
      catch(InputMismatchException ex) { wrong = true; }
      try { lectureCapacity = scanInt("Input lecture capacity"); }
      catch(InputMismatchException ex) { wrong = true; }
      if(wrong) System.out.println(Messages.WRONG_INPUTTYPE);
      else h.insertLecture(lectureName, lectureCredit, lectureCapacity);
      break;
    case 4:
      // id가 int로 파싱되지 않으면
      // WRONG_INPUTTYPE을 출력하고
      // remove는 시도하지 않는다.
      try { lectureId = scanInt("Input lecture id"); }
      catch(InputMismatchException ex) { wrong = true; }
      if(wrong) System.out.println(Messages.WRONG_INPUTTYPE);
      else h.removeLecture(lectureId);
      break;
    case 5:
      // id가 'nnnn-nnnnn' 형식이 아니면
      // INSERT_STUERR_FORMAT을 출력하고
      // insert는 시도하지 않는다.
      String studentName = scanString("Input student name");
      studentId = scanString("Input student id");
      wrong = !studentId.matches("\\d\\d\\d\\d\\-\\d\\d\\d\\d\\d");
      if(wrong) System.out.println(Messages.INSERT_STUERR_FORMAT);
      else h.insertStudent(studentName, studentId);
      break;
    case 6:
      studentId = scanString("Input student id");
      h.removeStudent(studentId);
      break;
    case 7:
      // lecture id가 int로 파싱되지 않으면
      // WRONG_INPUTTYPE을 출력하고
      // register는 시도하지 않는다.
      studentId = scanString("Input student id");
      try { lectureId = scanInt("Input lecture id"); }
      catch(InputMismatchException ex) { wrong = true; }
      if(wrong) System.out.println(Messages.WRONG_INPUTTYPE);
      else h.registerClass(studentId, lectureId);
      break;
    case 8:
      studentId = scanString("input student id");
      h.listLectures(studentId);
      break;
    case 9:
      try { lectureId = scanInt("Input lecture id"); }
      catch(InputMismatchException ex) { wrong = true; }
      if(wrong) System.out.println(Messages.WRONG_INPUTTYPE);
      else h.listStudents(lectureId);
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

  private static int tryScanInt(String prompt, int defaultValue) {
    try {
      return scanInt(prompt);
    }catch(InputMismatchException ex) {
      return defaultValue;
    }
  }

  private static int scanInt(String prompt) throws InputMismatchException {
    System.out.printf("%s: ", prompt);
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);
    return in.nextInt();
  }

  private static String scanString(String prompt) {
    System.out.printf("%s: ", prompt);
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);
    return in.nextLine();
  }
}
