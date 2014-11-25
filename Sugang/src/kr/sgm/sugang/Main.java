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
      int code = tryScanInt("Select your action", 0); // 잘못된 명령이 들어오면 종료
      switch(code) {
      case 1:
        h.listAllLectures();
        break;
      case 2:
        h.listAllStudents();
        break;
      case 3:
        break;
      case 4:
        break;
      case 5:
        break;
      case 6:
        break;
      case 7:
        break;
      case 8:
        break;
      case 9:
        break;
      default:
        // [1-9] 이외의 명령이 들어오면 종료
        exit = true;
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
