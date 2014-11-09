package kr.sgm.sql;

import java.util.ArrayList;
import java.util.Arrays;

final class PrettyPrinter {

  // 테이블 형태의 데이터를 출력한다.
  // hasHeader가 true이면 첫번째 행을 header로서 나타낸다.
  static void print(ArrayList<ArrayList<Object>> table, boolean hasHeader) {
    if(table.size() == 0) return;

    int[] maxLengths = new int[table.get(0).size()];
    for(ArrayList<Object> row : table) {
      int colIndex = 0;
      for(Object cell : row) {
        int l = cell == null ? 4 : cell.toString().length();
        if(l > maxLengths[colIndex])
          maxLengths[colIndex] = l;
        colIndex++;
      }
    }

    int totalLength = 1;
    for(int maxLength : maxLengths) {
      totalLength += maxLength + 1;
    }

    char[] lineChars = new char[totalLength];
    int currentIndex = 0;
    lineChars[currentIndex++] = '+';
    for(int maxLength : maxLengths) {
      Arrays.fill(lineChars, currentIndex, currentIndex + maxLength, '-');
      currentIndex += maxLength;
      lineChars[currentIndex++] = '+';
    }
    String line = new String(lineChars);

    int index = 0;
    System.out.println(line);
    if(hasHeader) {
      System.out.print('|');
      for(Object cell : table.get(0)) {
        String format = String.format("%%-%ds", maxLengths[index]);
        System.out.printf(format, cell == null ? "null" : cell.toString());
        System.out.print('|');
        index++;
      }
      System.out.println();
      table.remove(0);
      System.out.println(line);
    }
    for(ArrayList<Object> row : table) {
      index = 0;
      System.out.print('|');
      for(Object cell : row) {
        String format = String.format("%%-%ds", maxLengths[index]);
        System.out.printf(format, cell == null ? "null" : cell.toString());
        System.out.print('|');
        index++;
      }
      System.out.println();
    }
    System.out.println(line);
  }

  private PrettyPrinter() {
    // static class
  }

}
