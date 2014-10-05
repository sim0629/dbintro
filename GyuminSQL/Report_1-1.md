Database 2014 Fall Project 1-1
==============================
Simple SQL Parser
2009-11744 심규민

파일 구조
---------
하나의 프로젝트(GyuminSQL)와 하나의 패키지(kr.sgm.sql)가 있다.
Grammer.jj 파일 하나만 직접 작성되었으며, 다른 java 파일들은 javacc(1.5.27)로 Grammer.jj를 컴파일한 결과이다.

실행법
------
GyuminSQL 프로젝트를 이클립스에서 열어 실행하면 된다.

Parser
------
SQL 파싱을 위한 클래스로서, 프로그램의 진입점 main과 javacc에 의해 생성된 메서드들이 있다.

main에서 무한 루프를 돌며 사용자의 입력을 기다린다. 매번 Parser 인스턴스를 stdin을 받게 생성하고, Parse 메서드를 호출하여 파싱한다. 파싱을 하고 그 결과를 출력하는데, ParseException이 발생하면 "Syntax error"를 출력한다. Parse의 결과는 무슨 쿼리를 받았는지 그 종류만을 String으로 가지고 있다. 아직 해당 SQL문을 해석하지는 않는다. 쿼리 리스트를 입력 받을 수 있으므로 결과 타입은 ArrayList&lt;String&gt;이며 Parse 메서드의 바깥에서 인스턴스를 생성하여 인자로 넘겨준다. 쿼리 리스트의 중간에 ParseException이 발생하면 그 직전까지의 결과를 출력하고 에러메시지를 출력한다. exit 키워드가 입력될 때 Parse 메서드가 true를 리턴하고 루프를 탈출하고 프로그램이 종료된다.

TOKENs
------
공백 토큰에는 의미(semantics)가 없으므로 " ", "\t", "\r", "\n"은 SKIP한다. 공백은 문법(syntax)적으로 토큰을 구분하는 용도로 사용될 수 있다. 공백이 여러개인 경우나, 공백이 없어도 토큰 간의 구분이 명확한 경우 모두 제대로 파싱된다.
사용자 입력의 끝을 판단하기 위해, 세미콜론 직후에 개행이 있는 것을 EOP(End of Parsing)로 지정하였다. 따라서 세미콜론 뒤에 스페이스나 다른 키워드 문자가 오고 개행할 경우 입력을 계속 기다린다. EOP를 만나면 파싱을 끝낸다.
기본적으로 Grammer.pdf를 따라서 토큰을 지정하였다.
키워드들을 모두 토큰으로 지정하였다.
TABLE NAME, COLUMN NAME 등은 별도의 토큰으로 지정하지 않고 LEGAL IDENTIFIER를 사용하였다.
NON QUOTE SPECIAL CHARACTER는 "자판으로 입력가능한"의 의미가 애매하고 사실상 자판으로 모든 문자를 입력할 수 있으므로 토큰으로 하지 않았다. NON QUOTE CHARACTER는 QUOTE가 작은따옴표만 인정하므로 큰따옴표도 허용한 모든 문자로 하였다. 이렇게 하여도 CHAR STRING을 정의하는 데에 아무런 문제가 발생하지 않는다.
"*"를 STAR라는 이름으로 지정하였다.

Methods
-------
main에서 호출하는 Parse 메서드와, Grammer.pdf에 있는 것 중에 토큰을 제외한 것들이 있다.
대부분의 메서드는 Grammer.pdf에 정의된대로 나열하였다. 이번 프로젝트에서는 syntax checking만 하므로 Parse, Query를 제외하고는 리턴값이 void이다. Parse는 exit;을 받아 종료인지 아닌지를 참거짓으로 리턴하며, Query는 받은 쿼리의 종류를 문자열로 리턴한다.
BOOLEAN VALUE EXPRESSION과 BOOLEAN TERM의 recursion을 없애기 위해 x = a (or/and a)* 형태로 변경하였다.
일부 메서드는 파싱에 애매함이 생겨 LOOKAHEAD를 사용하여 애매함을 없앴다. 자세한 설명은 코드 상의 주석으로 남겨놓았다.

(문서 끝)
