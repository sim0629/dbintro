package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Persistent;

import java.util.Date;

@Persistent
public final class Value {
  private int i;
  private String s;
  private Date d;

  public void setInt(int i) {
    this.i = i;
  }
  public int getInt() {
    return this.i;
  }

  public void setString(String s) {
    this.s = s;
  }
  public String getString() {
    return this.s;
  }

  public void setDate(Date d) {
    this.d = d;
  }
  public Date getDate() {
    return this.d;
  }

  public static boolean same(DataType type, Value x, Value y) {
    if(type.isInt()) {
      return x.i == y.i;
    }else if(type.isChar()) {
      return x.s.equals(y.s);
    }else if(type.isDate()) {
      return x.d.equals(y.d);
    }else {
      throw new IllegalStateException();
    }
  }
}
