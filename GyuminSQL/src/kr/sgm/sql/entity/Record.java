package kr.sgm.sql.entity;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.util.ArrayList;
import java.util.UUID;

@Entity
public final class Record {
  @PrimaryKey
  private UUID uid;
  private ArrayList<Value> values = new ArrayList<Value>();

  public UUID getUID() {
    return this.uid;
  }
  public void setUID(UUID uid) {
    this.uid = uid;
  }

  public ArrayList<Value> getValues() {
    return new ArrayList<Value>(this.values);
  }
  public void setValues(ArrayList<Value> values) {
    this.values.clear();
    this.values.addAll(values);
  }
}
