package kr.sgm.sugang;

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

  public static void main(String[] args) throws Exception {
    QueryHandler h = new QueryHandler();
    boolean exit = false;
    while(!exit) {
      printMenu();
      // action은 int가 아닐 수도 있기 때문에
      // action이 int로 파싱되지 않는 것은
      // WRONG_INPUTTYPE이 아니다.
      // 기본값을 0으로 주고 WRONG_SELECTION으로 처리되게 한다.
      int action = tryScanInt("Select your action", 0);
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
        break;
      case 8:
        break;
      case 9:
        break;
      case 10:
        exit = true;
        break;
      default:
        System.out.printf(Messages.WRONG_SELECTION_DD, 1, 10);
        System.out.println();
        break;
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
