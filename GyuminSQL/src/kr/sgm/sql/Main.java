package kr.sgm.sql;

import java.util.ArrayList;

public class Main {
  private static String PROMPT = "SQL_2009-11744> ";

  public static void main(String[] args) {
    while(true) {
      System.out.print(PROMPT);
      Parser parser = new Parser(System.in);
      // 파싱 결과를 저장할 리스트.
      ArrayList<BaseQuery> results = new ArrayList<BaseQuery>();
      try {
        try {
          // Parse는 종료할지를 boolean 값으로 리턴한다.
          // 파싱 결과를 리턴값으로 받지 않고
          // 결과를 담을 리스트를 인자로 넘기는 이유는
          // ParseException이 발생하는 경우에도
          // 그 직전까지의 결과를 알기 위함이다.
          if(parser.Parse(results)) break;
        }finally {
          // Parse가 Exception을 발생하든 안하든 실행되는 block이다.
          for(BaseQuery result : results)
            result.run();
        }
      }catch(ParseException ex) {
        System.out.println("Syntax error");
      }
    }
  }
}
