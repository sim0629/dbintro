package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

abstract class QueryBooleanTest implements IWhereClause {
  public abstract Boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException;
}
