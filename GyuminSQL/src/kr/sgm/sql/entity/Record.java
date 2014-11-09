package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.ArrayList;

@Entity
public final class Record {
  @PrimaryKey
  private String uid;
  private ArrayList<Value> values = new ArrayList<Value>();

  public String getUID() {
    return this.uid;
  }
  public void setUID(String uid) {
    this.uid = uid;
  }

  public ArrayList<Value> getValues() {
    return new ArrayList<Value>(this.values);
  }
  public void setValues(ArrayList<Value> values) {
    this.values.clear();
    this.values.addAll(values);
  }
  public void setValue(int index, Value value) {
    this.values.set(index, value);
  }
}
