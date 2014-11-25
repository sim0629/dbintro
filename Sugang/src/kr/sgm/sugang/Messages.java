package kr.sgm.sugang;

final class Messages {
  static final String INSERT_LEC_SUCCESS =
    "A Lecture is successfully inserted.";
  static final String INSERT_STU_SUCCESS =
    "A Student is successfully inserted.";
  static final String DELETE_LEC_SUCCESS =
    "A Lecture is successfully deleted.";
  static final String DELETE_LEC_FAIL =
    "No such lecture.";
  static final String DELETE_STU_SUCCESS =
    "A Student is successfully deleted.";
  static final String DELETE_STU_FAIL =
    "No such student.";
  static final String INSERT_LECERR_CREDIT =
    "Credit should be over 0.";
  static final String INSERT_LECERR_CAPACITY =
    "Capacity should be over 0.";
  static final String INSERT_STUERR_FORMAT =
    "Id should have form 'nnnn-nnnnn'.";
  static final String INSERT_REGISTR_SUCCESS =
    "Applied.";
  static final String INSERT_REGISTRERR_CAPACITY =
    "Capacity of a lecture is full.";
  static final String INSERT_REGISTRERR_CREDIT =
    "No remaining credits.";

  private Messages() {
    throw new IllegalStateException("Don't instanciate it");
  }
}
