package kr.sgm.sql;

import java.util.ArrayList;

import kr.sgm.sql.entity.*;

interface IWhereClause {
  Boolean check(ArrayList<QueryReferedTable> referedTables, ArrayList<Table> tables, ArrayList<Record> records)
    throws InvalidQueryException;
}
