Database 2014 Fall Project 1-2
==============================
DDL implementaion
2009-11744 심규민

개발 환경
---------
- Java version 1.7
- JavaCC 1.5.27
- Berkeley DB JE (jar 포함되어 있음)

실행법
------
GyuminSQL 프로젝트를 이클립스에서 열어 실행하면 된다.

가정 및 특이사항
----------------
- Foreign key는 다른 테이블의 primary key만 참조할 수 있다. Primary key가 composite key일 경우에 key를 구성하는 일부 column만 참조될 수 없다. 이는 PostgreSQL, SQLPlus 등의 동작을 따르는 것이다.
- LEGAL IDENTIFIER는 대소문자를 구분하지 않으며, 사용자 입력을 받았을 때 Parser에서 모두 소문자로 바꾸어 처리한다.

구조와 설명
-----------
두개의 패키지 `kr.sgm.sql`, `kr.sgm.sql.entity`가 있다.

kr.sgm.sql.entity
-----------------
Berkeley db에 저장할 모델들을 담고 있다. 현재는 DDL만 구현되어 있으므로 테이블 정보만 저장할 수 있다.

- `Table.java` : 하나의 테이블을 표현하는 모델이다. 테이블 이름과 Column 리스트를 가지고 있다.
- `Column.java` : 테이블 스키마를 구성하는 하나의 컬럼을 표현하는 모델이다. 컬럼의 이름, DataType, NULL인지 아닌지, primary key인지 아닌지, foreign key인지 아닌지를 가지고 있다.
- `DataType.java` : 컬럼의 특성 중 데이터 타입을 표현하는 모델이다. `INT`, `CHAR(n)`, `DATE` 중 하나를 표현할 수 있다.

kr.sgm.sql
----------
기본 패키지로서 파일들을 의미적으로 분류하자면 Main, Parser, Query, QueryToken, DatabaseHandler, Messages가 있다.

### Main
- `Main.java` : 프로그램의 진입점 main 메서드가 있다. 사용자 입력을 지속적으로 받아들이는 루프가 있고, 입력을 파싱하여 쿼리를 구성하고 실행한다.

### Parser
- `Grammer.jj` : 문법 정의가 있다. 이를 javacc로 컴파일하여 Parser를 만들었다. javacc에 의해 자동 생성된 java 파일들(`ParseException.java`, `Parser.java`, `ParserConstants.java`, `ParserTokenManager.java`, `SimpleCharStream.java`, `TokenMgrError.java`)이 있다.
- `Parser.java` : 사용자 입력을 파싱하여 Query를 구성한다.

### Query
- `BaseQuery.java` : Parser가 구성하는 쿼리를 표현하는 객체들의 기본(부모)객체이다. 쿼리를 실행하는 `run()` 메서드가 있다.
- `*Query.java` : BaseQuery를 상속하여 각 종료의 쿼리를 구현한다. 현재는 `CreateTableQuery`, `DropTableQuery`, `DescribeQuery`, `ShowTablesQuery`가 있으며 이들은 모두 DDL로서 `IDefinitionQuery` interface를 구현한다.
- `IDefinitionQuery.java` : DDL 쿼리의 인터페이스로서 테이블 정보를 저장하는 DatabaseHandler 인스턴스를 하나 가지고 있다.

### QueryToken
- `Query*.java` : 쿼리를 파싱할 때 쿼리의 일부분을 나타내는 객체들로서, `QueryColumnDefinition`, `QueryDataType`, `QueryPrimaryKeyConstraint`, `QueryReferentialConstraint` 등이 있다.

### DatabaseHandler
- `DatabaseHandler.java` : Berkeley DB JE의 DPL api를 이용하여 객체를 저장하거나 얻어오는 기능을 제공하는 객체이다.

### Messages
- `Messages.java` : 프로그램이 출력하는 메시지(또는 메시지 포맷)들이 모여 있다.

느낀 점
-------
DDL만 구현하는 데에도 코딩 양이 많았다. Java 언어에는 C# 언어의 property와 같은 기능이 없어서 getter와 setter를 메서드로 전부 구현해줘야 하는 언어의 한계를 느꼈다.
Berkeley DB는 key/value store임에도 다른 nosql들에 비해 사용하기 어려웠다. Environment와 EnvironmentConfig 등을 만들어줘야 하는 등 기본 코드가 많다는 것이 원인이라고 생각한다. 

(문서 끝)
