Database 2014 Fall Project 1-3
==============================
Simple DBMS (DML implementation)
2009-11744 심규민

개발 환경
---------
- Java version 1.7
- JavaCC 1.5.27
- Berkeley DB JE (jar 포함되어 있음)

실행법
------
GyuminSQL 프로젝트를 이클립스에서 열어 실행하면 된다.

가정
----
- `select` 문에서 주어진 column list와 value list의 길이가 다르면 TypeMismatch 에러를 낸다.
- `insert` 문에서 column list에 중복된 이름은 나오지 않는다.
- 한 컬럼은 둘 이상의 테이블을 참조하는 foreign key가 될 수 없다.

구현된 것
---------
- 스펙에 주어진 DDL, DML 모두 구현되었다.
- `from` 절에서 `as`를 사용할 수 있다.
- `select` 쿼리의 column list에 `as`를 사용할 수 있다.
- 지난 프로젝트에서 `show tables` 쿼리 결과가 비었을 때 NoTable을 보여주는 것을 구현하지 않았는데 이번에 구현되었다.
- `select` 쿼리 결과를 예쁜 테이블 형태로 보여준다.
- `select` 쿼리 결과가 비어 있을 때에는 column name 헤더만 보이는 테이블이 출력된다.
- `show tables`, `desc` 쿼리 결과도 `select` 쿼리 결과와 같은 형태로 보여주게 통일하였다. (더이상 긴 결과를 자르지 않는다.)

주요 모듈
---------
전체적인 구조는 지난 프로젝트와 변함이 없다.

구현에 대한 설명
----------------
이번에 추가된 기능인 DML에 대한 설명만 하겠다.

### 공통
각 table 마다 EntityStore를 만들어 저장한다. EntityStore는 DPL에서 제공하는 것이며, 여기서는 이를 감싸는 DataHandler를 만들어 사용한다. Table DataHandler의 entity 타입은 Record이다. 각 Record는 UUID를 String 타입으로 가지고 있으며 이를 EntityStore의 PrimaryKey로 사용한다. Record에 들어있는 값들은 ArrayList of Value로 들고있다. 각 Value에는 타입을 지정하지 않은 채 int, String, Date 중 하나를 가질 수 있다. 타입은 테이블 정보(infoHandler)를 통해 알아낸다.

### select
run을 하면 쿼리에 언급된 각각의 테이블들에서 모든 레코드를 가져온다. 이들의 모든 조합에 대해 where 조건에 맞는지 검사를 한다. 조건에 맞는 레코드의 조합에 한하여 버퍼에 모아둔다. 모든 조합에 대해 검사가 끝나면 버퍼에 있는 것들을 쿼리에 언급된 컬럼만 출력한다.

### insert
run을 하면 쿼리에 들어온 값들을 레코드로 만들고, primary key와 foreign key 제약을 검사한 뒤 저장한다.

### delete
run을 하면 해당 테이블에서 모든 레코드를 가져온다. 각 레코드에 대해 where 조건을 검사하고 해당 되는 것들을 모아둔다. 이들에 대해 foreign key 제약을 검사하고 필요한 경우 다른 테이블을 업데이트 한다. 지울 수 있는 레코드들을 지우고, 지울 수 없는 것들은 지우지 않다. 지운 것과 지우지 못한 것의 개수를 출력한다.

### where
three-value logic을 구현하기 위해 Boolean 타입을 사용하고 unknown은 null로 표현하였다.

느낀 점
-------
- 간단한 기능만 있는 DBMS를 구현하는 데에도 코딩할 양이 많았다.
- Index는 구현하지 못해서 성능이 많이 떨어지는 것이 아쉽다.
- 널리 쓰이는 DBMS들은 만들기 훨씬 어려울 것이다.

(문서 끝)
