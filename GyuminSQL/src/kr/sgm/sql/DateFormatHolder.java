package kr.sgm.sql;

import java.util.Date;

public final class DateFormatHolder {
  private static java.text.SimpleDateFormat dateFormat =
    new java.text.SimpleDateFormat("yyyy-MM-dd");

  static {
    // 형식이 잘못된 입력에 대해 parse할 때
    // ParseException을 내도록 설정 한다.
    dateFormat.setLenient(false);
  }

  public static Date parse(String s) throws java.text.ParseException {
    return dateFormat.parse(s);
  }

  public static String format(Date d) {
    return dateFormat.format(d);
  }

  private DateFormatHolder() {
    // static class
  }
}
