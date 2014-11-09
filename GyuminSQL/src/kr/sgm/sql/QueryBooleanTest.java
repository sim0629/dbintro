package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

abstract class QueryBooleanTest implements IWhereClause {
  public abstract boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Record> records)
    throws InvalidQueryException;
}
