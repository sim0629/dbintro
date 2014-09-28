/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package kr.sgm.sql;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SEMICOLON = 5;
  /** RegularExpression Id. */
  int QUOTE = 6;
  /** RegularExpression Id. */
  int NON_QUOTE_CHARACTER = 7;
  /** RegularExpression Id. */
  int ALPHABET = 8;
  /** RegularExpression Id. */
  int SIGN = 9;
  /** RegularExpression Id. */
  int DIGIT = 10;
  /** RegularExpression Id. */
  int INT_VALUE = 11;
  /** RegularExpression Id. */
  int NN = 12;
  /** RegularExpression Id. */
  int NNNN = 13;
  /** RegularExpression Id. */
  int DATE_VALUE = 14;
  /** RegularExpression Id. */
  int CHAR_STRING = 15;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"\\n\"",
    "\";\"",
    "<QUOTE>",
    "<NON_QUOTE_CHARACTER>",
    "<ALPHABET>",
    "<SIGN>",
    "<DIGIT>",
    "<INT_VALUE>",
    "<NN>",
    "<NNNN>",
    "<DATE_VALUE>",
    "<CHAR_STRING>",
  };

}
