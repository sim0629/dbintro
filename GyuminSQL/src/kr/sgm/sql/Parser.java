/* Generated By:JavaCC: Do not edit this line. Parser.java */
package kr.sgm.sql;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser implements ParserConstants {
  private static String PROMPT = "SQL_2009-11744> ";

  // 키워드 목록
  //  식별자에 포함되지 않는지 확인하기 위해 사용된다.
  //  아래의 Keywords 토큰과 같은 집합을 유지해야한다.
  private static ArrayList<String> Keywords =
    new ArrayList<String>(
      Arrays.asList(
        "exit",
        "create",
        "table",
        "not",
        "null",
        "primary",
        "key",
        "foreign",
        "references",
        "int",
        "char",
        "date",
        "drop",
        "show",
        "tables",
        "desc",
        "select",
        "as",
        "from",
        "where",
        "or",
        "and",
        "is",
        "insert",
        "into",
        "values",
        "delete"
      )
    );

  // 식별자가 키워드 목록에 포함되는지 검사한다.
  // 대소문자 구분없이 비교한다.
  private static boolean isKeyword(String identifier) {
    return Keywords.contains(identifier.toLowerCase());
  }

  public static void main(String[] args) {
    while(true) {
      System.out.print(PROMPT);
      Parser parser = new Parser(System.in);
      // 파싱 결과를 저장할 리스트.
      ArrayList<String> results = new ArrayList<String>();
      try {
        try {
          // Parse는 종료할지를 boolean 값으로 리턴한다.
          // 파싱 결과를 리턴값으로 받지 않고
          // 결과를 담을 리스트를 인자로 넘기는 이유는
          // ParseException이 발생하는 경우에도
          // 그 직전까지의 결과를 알기 위함이다.
          if(parser.Parse(results)) break;
        }finally{
          // Parse가 Exception을 발생하든 안하든 실행되는 block이다.
          for(String result : results)
            System.out.printf("\u005c"%s\u005c" requested\u005cn", result);
        }
      }catch(ParseException ex) {
        System.out.println("Syntax error");
      }
    }
  }

  final public boolean Parse(ArrayList<String> results) throws ParseException {
  boolean exit;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case EXIT:
      jj_consume_token(EXIT);
      exit = true;
      break;
    case CREATE:
    case DROP:
    case SHOW:
    case DESC:
    case SELECT:
    case INSERT:
    case DELETE:
      QueryList(results);
      exit = false;
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(EOP);
    {if (true) return exit;}
    throw new Error("Missing return statement in function");
  }

  final public void QueryList(ArrayList<String> results) throws ParseException {
  String result;
    result = Query();
    results.add(result);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SEMICOLON:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_1;
      }
      jj_consume_token(SEMICOLON);
      result = Query();
      results.add(result);
    }
  }

  final public String Query() throws ParseException {
  String result;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CREATE:
      CreateTable();
      result = "create table";
      break;
    case DROP:
      DropTable();
      result = "drop table";
      break;
    case SHOW:
      ShowTables();
      result = "show tables";
      break;
    case DESC:
      Describe();
      result = "desc";
      break;
    case SELECT:
      Select();
      result = "select";
      break;
    case INSERT:
      Insert();
      result = "insert";
      break;
    case DELETE:
      Delete();
      result = "delete";
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return result;}
    throw new Error("Missing return statement in function");
  }

  final public void CreateTable() throws ParseException {
    jj_consume_token(CREATE);
    jj_consume_token(TABLE);
    LegalIdentifier();
    TableElementList();
  }

  final public void DropTable() throws ParseException {
    jj_consume_token(DROP);
    jj_consume_token(TABLE);
    LegalIdentifier();
  }

  final public void ShowTables() throws ParseException {
    jj_consume_token(SHOW);
    jj_consume_token(TABLES);
  }

  final public void Describe() throws ParseException {
    jj_consume_token(DESC);
    LegalIdentifier();
  }

  final public void TableElementList() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    TableElement();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(COMMA);
      TableElement();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  final public void TableElement() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEGAL_IDENTIFIER:
      ColumnDefinition();
      break;
    case PRIMARY:
    case FOREIGN:
      TableConstraintDefinition();
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void ColumnDefinition() throws ParseException {
    LegalIdentifier();
    DataType();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      jj_consume_token(NULL);
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
  }

  final public void TableConstraintDefinition() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PRIMARY:
      PrimaryKeyConstraint();
      break;
    case FOREIGN:
      ReferentialConstraint();
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void DataType() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      jj_consume_token(INT);
      break;
    case CHAR:
      jj_consume_token(CHAR);
      jj_consume_token(LEFT_PAREN);
      jj_consume_token(INT_VALUE);
      jj_consume_token(RIGHT_PAREN);
      break;
    case DATE:
      jj_consume_token(DATE);
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void PrimaryKeyConstraint() throws ParseException {
    jj_consume_token(PRIMARY);
    jj_consume_token(KEY);
    ColumnNameList();
  }

  final public void ReferentialConstraint() throws ParseException {
    jj_consume_token(FOREIGN);
    jj_consume_token(KEY);
    ColumnNameList();
    jj_consume_token(REFERENCES);
    LegalIdentifier();
    ColumnNameList();
  }

  final public void ColumnNameList() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    LegalIdentifier();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_3;
      }
      jj_consume_token(COMMA);
      LegalIdentifier();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  final public void Select() throws ParseException {
    jj_consume_token(SELECT);
    SelectList();
    TableExpression();
  }

  final public void SelectList() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case STAR:
      jj_consume_token(STAR);
      break;
    case LEGAL_IDENTIFIER:
      SelectedColumn();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[9] = jj_gen;
          break label_4;
        }
        jj_consume_token(COMMA);
        SelectedColumn();
      }
      break;
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void SelectedColumn() throws ParseException {
    if (jj_2_1(2)) {
      LegalIdentifier();
      jj_consume_token(PERIOD);
    } else {
      ;
    }
    LegalIdentifier();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AS:
      jj_consume_token(AS);
      LegalIdentifier();
      break;
    default:
      jj_la1[11] = jj_gen;
      ;
    }
  }

  final public void TableExpression() throws ParseException {
    FromClause();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case WHERE:
      WhereClause();
      break;
    default:
      jj_la1[12] = jj_gen;
      ;
    }
  }

  final public void FromClause() throws ParseException {
    jj_consume_token(FROM);
    TableReferenceList();
  }

  final public void TableReferenceList() throws ParseException {
    ReferedTable();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_5;
      }
      jj_consume_token(COMMA);
      ReferedTable();
    }
  }

  final public void ReferedTable() throws ParseException {
    LegalIdentifier();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case AS:
      jj_consume_token(AS);
      LegalIdentifier();
      break;
    default:
      jj_la1[14] = jj_gen;
      ;
    }
  }

  final public void WhereClause() throws ParseException {
    jj_consume_token(WHERE);
    BooleanValueExpression();
  }

  final public void BooleanValueExpression() throws ParseException {
    BooleanTerm();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_6;
      }
      jj_consume_token(OR);
      BooleanTerm();
    }
  }

  final public void BooleanTerm() throws ParseException {
    BooleanFactor();
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[16] = jj_gen;
        break label_7;
      }
      jj_consume_token(AND);
      BooleanFactor();
    }
  }

  final public void BooleanFactor() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      break;
    default:
      jj_la1[17] = jj_gen;
      ;
    }
    BooleanTest();
  }

  final public void BooleanTest() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEGAL_IDENTIFIER:
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:
      Predicate();
      break;
    case LEFT_PAREN:
      ParenthesizedBooleanExpression();
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void ParenthesizedBooleanExpression() throws ParseException {
    jj_consume_token(LEFT_PAREN);
    BooleanValueExpression();
    jj_consume_token(RIGHT_PAREN);
  }

  final public void Predicate() throws ParseException {
    if (jj_2_2(4)) {
      NullPredicate();
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LEGAL_IDENTIFIER:
      case CHAR_STRING:
      case INT_VALUE:
      case DATE_VALUE:
        ComparisonPredicate();
        break;
      default:
        jj_la1[19] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void ComparisonPredicate() throws ParseException {
    CompOperand();
    jj_consume_token(COMP_OP);
    CompOperand();
  }

  final public void CompOperand() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:
      ComparableValue();
      break;
    case LEGAL_IDENTIFIER:
      if (jj_2_3(2)) {
        LegalIdentifier();
        jj_consume_token(PERIOD);
      } else {
        ;
      }
      LegalIdentifier();
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void ComparableValue() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT_VALUE:
      jj_consume_token(INT_VALUE);
      break;
    case CHAR_STRING:
      jj_consume_token(CHAR_STRING);
      break;
    case DATE_VALUE:
      jj_consume_token(DATE_VALUE);
      break;
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void NullPredicate() throws ParseException {
    if (jj_2_4(2)) {
      LegalIdentifier();
      jj_consume_token(PERIOD);
    } else {
      ;
    }
    LegalIdentifier();
    NullOperation();
  }

  final public void NullOperation() throws ParseException {
    jj_consume_token(IS);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NOT:
      jj_consume_token(NOT);
      break;
    default:
      jj_la1[22] = jj_gen;
      ;
    }
    jj_consume_token(NULL);
  }

  final public void Insert() throws ParseException {
    jj_consume_token(INSERT);
    jj_consume_token(INTO);
    LegalIdentifier();
    InsertColumnsAndSource();
  }

  final public void InsertColumnsAndSource() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LEFT_PAREN:
      ColumnNameList();
      break;
    default:
      jj_la1[23] = jj_gen;
      ;
    }
    ValueList();
  }

  final public void ValueList() throws ParseException {
    jj_consume_token(VALUES);
    jj_consume_token(LEFT_PAREN);
    Value();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case COMMA:
        ;
        break;
      default:
        jj_la1[24] = jj_gen;
        break label_8;
      }
      jj_consume_token(COMMA);
      Value();
    }
    jj_consume_token(RIGHT_PAREN);
  }

  final public void Value() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NULL:
      jj_consume_token(NULL);
      break;
    case CHAR_STRING:
    case INT_VALUE:
    case DATE_VALUE:
      ComparableValue();
      break;
    default:
      jj_la1[25] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void Delete() throws ParseException {
    jj_consume_token(DELETE);
    jj_consume_token(FROM);
    LegalIdentifier();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case WHERE:
      WhereClause();
      break;
    default:
      jj_la1[26] = jj_gen;
      ;
    }
  }

  final public void LegalIdentifier() throws ParseException {
  Token t;
    t = jj_consume_token(LEGAL_IDENTIFIER);
    // 식별자가 키워드일 경우 예외를 발생한다.
    // 키워드 토큰은 대소문자 구분을 해서 소문자만 되지만
    // 식별자는 대소문자 구분을 하지 않으므로
    // 이러한 예외처리가 필요하다.
    if(isKeyword(t.image))
      {if (true) throw new ParseException();}
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  private boolean jj_3R_9() {
    if (jj_scan_token(LEGAL_IDENTIFIER)) return true;
    return false;
  }

  private boolean jj_3_3() {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_10()) return true;
    return false;
  }

  private boolean jj_3_4() {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_9()) return true;
    if (jj_scan_token(PERIOD)) return true;
    return false;
  }

  private boolean jj_3R_11() {
    if (jj_scan_token(IS)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(10)) jj_scanpos = xsp;
    if (jj_scan_token(NULL)) return true;
    return false;
  }

  private boolean jj_3R_10() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_4()) jj_scanpos = xsp;
    if (jj_3R_9()) return true;
    if (jj_3R_11()) return true;
    return false;
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[27];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40d80180,0x40,0x40d80100,0x0,0x5000,0x400,0x5000,0x70000,0x0,0x0,0x0,0x1000000,0x4000000,0x0,0x1000000,0x8000000,0x10000000,0x400,0x0,0x0,0x0,0x0,0x400,0x0,0x0,0x800,0x4000000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x2,0x0,0x2,0x40,0x4,0x0,0x0,0x0,0x40,0x40,0x84,0x0,0x0,0x40,0x0,0x0,0x0,0x0,0x4c14,0x4c04,0x4c04,0x4c00,0x0,0x10,0x40,0x4c00,0x0,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[4];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 27; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[51];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 27; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 51; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 4; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
