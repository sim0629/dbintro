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

  public static void main(String[] args) {
    boolean exit = false;
    while(!exit) {
      printMenu();
      switch(readCode()) {
      case 1:
        break;
      case 2:
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

  private static int readCode() {
    System.out.print("Select your action: ");
    @SuppressWarnings("resource")
    Scanner in = new Scanner(System.in);
    try {
      return in.nextInt();
    }catch(InputMismatchException ex) {
      return 0;
    }
  }
}
