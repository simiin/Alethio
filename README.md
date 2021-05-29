How To Test
===
Run
---
- src/main/java/com/alethio/shop/ShopApplication.java

Setting H2 Database
---
1. H2 프로그램 설치 및 접속
	- Web Console로 접속하기 전에 H2로 database를 만들어놓아야 함
2. Application 실행 후, Web Console 접속 (localhost:8080/h2-console)
	- Saved Settings (저장한 설정) : H2 Embedded
	- Driver Class (드라이버 클래스) : org.h2.Driver
	- JDBC URL
		- 내장 Server 가동 시, console창의 o.s.b.a.h2.H2ConsoleAutoConfiguration 항목의 주소(jdbc:h2:mem:~~~~) 입력
	- User Name (사용자명) : sa
	- Password (비밀번호) : (공백)
	

---


Using PostMan
---
- localhost:8080/order POST
	- Normal
		- {item_id : 1, amount: 10, contact_email: "test01@test.com    ", contact_name: "김철수", contact_mobile: "    01012345678  "}
	- Error : Item Not Enough (수량 부족)
		- {item_id : 1, amount: 1000, contact_email: "test01@test.com    ", contact_name: "김철수", contact_mobile: "    01012345678  "}
	- Error : Item Not Found (존재하지 않는 물품)
		- {item_id : 100, amount: 10, contact_email: "test01@test.com    ", contact_name: "김철수", contact_mobile: "    01012345678  "}


Test Code
---
- src/test/java/com/alethio/shop의 각 package에 기능 별로 Test Code 작성
- 각 파일 실행


---


Making History
===

Environment
---
- Ubuntu 20.04 LTS
- Java 1.8
- Spring Boot 2.5.0 + Maven
- H2 Database 1.4.200
- Eclipse 4.19.0


---


7days Plan
---
- 23 ~ 24
	- 개발환경 구축
	- 기능 / View / Server / DB 설계
	- View 완성
	- 모르는 영역 공부
- 25 ~ 27
	- back-end 전반
- 28 ~ 29
	- 최종 test보수
	- README.md 완성
	- 최종 완성 & 제줄
- 30
	- 제출 (13:00 전에)


---


Initial Settings
---
- Spring Boot Project 생성
	- spring initializr 사용
	- Dependencies
		- Spring Web
		- Spring JDBC
		- H2 Database
		- Thymeleaf
		- Lombok
	
	
---


Functions
---
- 중요한 기능을 최소한으로 구성
- Order
	- item list
	- ordering
- Manage
	- category
		- regist
		- list
		- delete
	- item
		- regist
		- list
		- update
		- delete
	- Order
		- list
		- delete
	- Restock
		- regist
		- list
		- delete


---


View
---
- 핵심 기능 접근이 쉽게
	- MVP의 특징 살리기 위해
- 만들기 편하게
	- 중요한 건 back-end


---


Server
---
- MVP 기능
	- 고객은 음식 혹은 옷을 주문할 수 있다
- MVP
	- 최소 기능 제품 (Minimum Viable Product)
	- 핵심 기능을 간결하고 완성도 있게 구현
- MSA
	- 의존성을 최대한 낮추어서 개발
	- Model2 방식 사용하여 의존성 Down
	- Java 코드로만 이루어진 Service와 DAO는 재사용 가능하게 만들기
		- Service 객체를 interface로 만들어, ServiceImpl Class가 상속 받도록 함
			- 다형성을 통해 개발 코드를 수정하지 않고 사용하는 객체를 변경할 수 있음
			- 기존 구현 객체(ServiceImpl1)와 비스니스 로직이 다른 기능을 추가해야할 경우, 다른 구현 객체(ServiceImpl2)를 만들어 사용할 수 있음
		- DAO를 interface로 만들어 하위 Class에 상속
			- 추후 Mybatis 등의 Mapper로 개발할 때, 사용 중인 Class를 고쳐쓰지 않고, 따로 Mybatis~~DAO.class를 따로 만들어 사용


---


DataBase
---
- 필수) item이 추가될 수 있어야 함
- 추가) category가 추가될 수 있어야 함
- table 간에 부모 자식 관계를 위한 foreign key를 제외하고서 다른 table의 column을 넣지 않음
	- 의존성을 낮추고 응집도를 높이기 위해 정규화에 집중
	- 성능을 위한 반정규화는 고려하지 않음
- Tables
	- category
		- id : pk
		- name
	- item
		- id : pk
		- category_id : fk
		- name
		- stock
	- orders
		- id : pk
		- item_id : fk
		- amount
		- contact_email
		- contact_name
		- contact_mobile
	- restock
		- id : pk
		- item_id : fk
		- company_name
		- encrypt_item_name
		- amount


---


Domains
---
- 값을 저장하기 위한 변수명은 각 table의 column명과 동일하나 join문을 이용하기 위한 변수들이 추가됨
- Category
	- id
	- name
- Item
	- id
	- category_id
	- name
	- stock
	- category_name (join)
- Order
	- id
	- item_id
	- amount
	- contact_email
	- contact_name
	- contact_mobile
	- item_name (join)
	- category_name (join)
- Restock
	- id
	- item_id
	- company_name
	- encrypt_item_name
	- amount
	- item_name (storing value)
		- encrypt_item_name을 만들기 위해 view에서 값을 받아오는 역할
		- db에 저장되는 값은 아님 (item_id가 있기 때문)
	- category_name (join)


---


/manage/category
---
- 최상위 부모가 되는 테이블과 관련된 category 기능 먼저 구현
- category가 최상위 부모이므로, category를 삭제하면 자식 관계인 item이 모두 지워짐
- category를 삭제할 때 GET방식을 사용하면 url을 입력하여 접근 가능하기 때문에 POST방식으로 category_id를 전송하여 처리
	- ajax를 사용하지 않고 post 방식으로 전송하기 위해 form이 필요함
	- category의 모든 항목에 form tag를 사용할 수 없으므로, form tag를 즉석으로 만들어 전송하는 함수(sendPost()) 구현 및 사용
		- cf) http://blog.kgom.kr/47


---

/manage/item
---
- order와 restock의 부모가 되는 item에 관련된 기능 구현
- 


---


/manage/restock
---
-


---


/order
---







---


개선할 점 & 아쉬운 점
---
- 주문 기록은 item이나 category를 변경해도 유지되어야 함






---


Trouble Shootings
---
- H2 Database Connect
	- Error: Database "mem:testdb" not found, either pre-create it or allow remote database creation (not recommended in secure environments) [90149-200]
	- 원인 : 최근 버전의 경우, 보안상의 이유로 Web Console에서 새 database를 작성할 수 없음
	- 해결
		- 별도의 H2 프로그램 설치 및 접속 
		- Spring Boot의 Server 실행 시 H2 Embedded로 접속할 수 있는 주소 할당됨 (console에 나옴)
		- H2 Embedded에 할당된 주소 입력 후 접속


---


References
---
- MSA (MicroService Architecture)
	- http://clipsoft.co.kr/wp/blog/%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98msa-%EA%B0%9C%EB%85%90/
- MVP : 최소 기능 제품
	- https://ko.wikipedia.org/wiki/%EC%B5%9C%EC%86%8C_%EA%B8%B0%EB%8A%A5_%EC%A0%9C%ED%92%88
- 내부 DB
	- https://ko.wikipedia.org/wiki/%EC%9D%B8%EB%A9%94%EB%AA%A8%EB%A6%AC_%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4
- H2
	- Commands
		- https://www.h2database.com/html/commands.html
	- Wiki
		- https://ko.wikipedia.org/wiki/H2_(DBMS)
	- Video
		- https://www.youtube.com/watch?v=YywLS8XdxLQ
	- How to
		- https://sosohanya.tistory.com/27
		- https://dev-woo.tistory.com/45
	- Initializing Database
		- https://docs.spring.io/spring-boot/docs/2.1.4.RELEASE/reference/htmlsingle/#howto-initialize-a-database-using-spring-jdbc
	- Spring Boot + Mybatis + H2
		- https://atoz-develop.tistory.com/entry/Spring-Boot-MyBatis-%EC%84%A4%EC%A0%95-%EB%B0%A9%EB%B2%95	
- JdbcTemplate
	- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
	- How to
		- https://velog.io/@lacomaco/Spring-Jdbc-Template-%EC%8B%A4%EC%8A%B5-%EC%A0%95%EB%A6%AC
		- http://wonwoo.ml/index.php/post/1960
	- BeanPropertyRowMapper
		- https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/BeanPropertyRowMapper.html
- Thymeleaf
	- https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html		
- 교차 출처 리소스 공유 (CORS) 설정
- CI / CD
	- https://www.redhat.com/ko/topics/devops/what-is-ci-cd
- PostMan
	- install
		- https://linuxize.com/post/how-to-install-postman-on-ubuntu-20-04/





