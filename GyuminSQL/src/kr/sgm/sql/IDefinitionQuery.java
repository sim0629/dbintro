package kr.sgm.sql;

import kr.sgm.sql.entity.Table;

interface IDefinitionQuery {
  DatabaseHandler<String, Table> infoHandler = new DatabaseHandler<String, Table>("_info", String.class, Table.class);
}
