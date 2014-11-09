package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Persistent;

import java.util.Date;

import kr.sgm.sql.DateFormatHolder;

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

  @Override
  public String toString() {
    if(this.d != null)
      return DateFormatHolder.format(this.d);
    else if(this.s != null)
      return this.s.toString();
    else
      return new Integer(this.i).toString();
  }
}
