package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

interface IWhereClause {
  boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Record> records)
    throws InvalidQueryException;
}
