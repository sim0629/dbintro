package kr.sgm.sql;

final class Messages {
  static final String TemporarySuccessS = "'%s' requested";
  static final String SyntaxError = "Syntax error";
  static final String CreateTableSuccessS = "'%s' table is created";
  static final String DuplicateColumnDefError = "Create table has failed: column definition is duplicated";
  static final String DuplicatePrimaryKeyDefError = "Create table has failed: primary key definition is duplicated";
  static final String ReferenceTypeError = "Create table has failed: foreign key references wrong type";
  static final String ReferenceNonPrimaryKeyError = "Create table has failed: foreign key references non primary key column";
  static final String ReferenceColumnExistenceError = "Create table has failed: foreign key references non existing column";
  static final String ReferenceTableExistenceError = "Create table has failed: foreign key references non existing table";
  static final String NonExistingColumnDefErrorS = "Create table has failed: '%s' does not exists in column definition";
  static final String TableExistenceError = "Create table has failed: table with the same name already exists";
  static final String InsertResult = "The row is inserted";
  static final String InsertDuplicatePrimaryKeyError = "Insertion has failed: Primary key duplication";
  static final String InsertReferentialIntegrityError = "Insertion has failed: Referential integrity violation";
  static final String InsertTypeMismatchError = "Insertion has failed: Types are not matched";
  static final String InsertColumnExistenceErrorS = "Insertion has failed: '%s' does not exist";
  static final String InsertColumnNonNullableErrorS = "Insertion has failed: '%s' is not nullable";
  static final String DeleteResultD = "%d row(s) are deleted";
  static final String DeleteReferentialIntegrityPassedD = "%d row(s) are not deleted due to referential integrity";
  static final String SelectTableExistenceErrorS = "Selection has failed: '%s' does not exist";
  static final String SelectColumnResolveErrorS = "Selection has failed: fail to resolve '%s'";
  static final String SelectNonComparableTypesError = "Where condition error: types are not matched in comparison";
  static final String SelectDuplicateAliasingError = "Alias is duplicated";
  static final String DropSuccessS = "'%s' table is dropped";
  static final String DropReferencedTableErrorS = "Drop table has failed: '%s' is referenced by other table";
  static final String ShowTablesNoTable = "There is no table";
  static final String NoSuchTable = "No such table";
  static final String InvalidDateRangeError = "Date value is in incorrect range";
  static final String CharLengthError = "Char length should be > 0";
  static final String WhereIncomparableError = "Where clause try to compare incomparable values";
  static final String WhereTableNotSpecified = "Where clause try to reference tables which are not specified";
  static final String WhereColumnNotExist = "Where clause try to reference non existing column";
  static final String WhereAmbiguousReference = "Where clause contains ambiguous reference";

  private Messages() {
    throw new IllegalStateException();
  }
}
