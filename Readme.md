# 플러시
* 변경감지 ( 더티 체킹)
* 수정된 엔티티 쓰기 지연 ( SLQ ) 저장소에 등록
* 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송 ( 등록, 수정, 삭제 쿼리)
* ==> 쉽게 말해 영속성 컨텍스트의 변경 내용을 DB에 반영하는 것을 말
  * flush가 발생한다고 해서 커밋이 되는게 아님 --> 실제 커밋에서 일어남
  * JPQL 쿼리 실행시 플러시가 자동으로 호출
   <pre><code> em.persist(memberA); 일 때  중간에 JQPL 실행 하면
   query - em.createQuery("select m form member as m", Member.class);
   List<Member> members = query.getResultList(); << 자동으로 flush가 되지 않으면 데이터가 조회 되지 않음
  </code></pre>  
* em.setFlushMode(FlushModeType.COMMIT)  >> 쓸 일 없음
  * FlushModeType.AUTO : 커밋이나 쿼리를 실행할 때 플러시 ( 기본값 )
  * FlushModeType.COMMIT : 커밋만 할때 플러시
* 영속성 컨텍스트를 비우지 않음
* 영속성 컨텍스트의 변경 내용을 데이터 베이스에 동기화
* 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됌

( 영속상태 : JPA가 관리하는 단계 )
# 준영속 상태 
* 영속 -> 준영속
* 영속 상태의 엔티티가 영속성 컨텍스트에서 분리 ( detached )
* 영속성 컨텍스트가 제공하는 기능을 사용 못함

# 엔티티 매핑
* 객체와 테이블 매핑 : @Entity, @Table
  * @Entity가 붙으면 클래스는 JPA가 관리, 엔티티라고 한다.
  * JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
    * 기본 생성자 필수 ( 파라미터가 없는 public 또는 protected 생성자)
    * final 클래스, enum, interface, inner 클래스 사용 할 수 없음
    * 저장할 필드에 final 사용 할 수 없듦
* 플드와 컬럼 매핑 : @Column
* 기본 키 매핑 : @id
* 연관관계 매핑 : @ManyToOne, @JoinColumn
* 데이터베이스 스키마 자동 생성
  * DDL을 애플리케이션 실행 시점에 자동 생성
  * 테이블 중심 -> 객체 중심
  * 데이터베이스 방언을 활용해서 데이터베이스에 맞는 적절한
    DDL 생성 이렇게 생성된 DDL은 개발 장비에서만 사용, 생성된 DDL은 운영서버에서는 사용하지 않거나, 적절히 다듬은 후 사용
    * create 기존테이블 삭제 후 다시 생성 (DROP + CREATE)
    * create-drop create와 같으나 종료시점에 테이블 DROP
    * update 변경분만 반영(운영DB에는 사용하면 안됨 --> 생성만 되고 지우는건 안된다)
    * validate 엔티티와 테이블이 정상 매핑되었는지만 확인
    * none 사용하지 않음
* 운영 장비에는 절대 create, create-drop, update 사용하면
    안된다.
* 개발 발 초기 단계는 create 또는 update , 테스트 서버는 update 또는 validate, 스테이징과 운영 서버는 validate 또는 none
* DDL 생성 기능
  * 제약조건 추가: 회원 이름은 필수, 10자 초과X
    * @Column(nullable = false, length = 10)
    * 유니크 제약조건 추가
    * @Table(uniqueConstraints = {@UniqueConstraint( name = "NAME_AGE_UNIQUE",
    columnNames = {"NAME", "AGE"} )})
  * DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고
    JPA의 실행 로직에는 영향을 주지 않는다.


# 필드와 컬럼매핑
* @Column 컬럼 매핑
  *  name : 필드와 매핑할 테이블의 컬럼 이름 ( 기본값 객체의 필드 이름 )
  *  insertable, updatable  :등록, 변경 가능 여부  ( 기본값 TRUE )
  *  nullable(DDL) : null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에
    not null 제약조건이 붙는다.
  *  unique(DDL) : @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제
    약조건을 걸 때 사용한다.
  *  columnDefinition (DDL) : 데이터베이스 컬럼 정보를 직접 줄 수 있다.
    ex) varchar(100) default ‘EMPTY' ( 기본값 필드의 자바 타입과 방언 정보를 사용해서 적절한 컬럼 타입 )
  * length(DDL) : 문자 길이 제약조건, String 타입에만 사용한다.  (기본값 255)
  *  precision, scale(DDL) : BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다). precision은 소수점을 포함한 전체 자 릿수를, scale은 소수의 자릿수 다. 참고로 double, float 타입에는 적용되지 않는다. 아주 큰 숫자나 정 밀한 소수를 다루어야 할 때만 사용한다.
     ( 기본값 precision=19, scale=2 )
* @Temporal 날짜 타입 매핑
* @Enumerated enum 타입 매핑
  * value : EnumType.ORDINAL: enum 순서를 데이터베이스에 저장, EnumType.STRING: enum 이름을 데이터베이스에 저장
    ( 기본값 EnumType.ORDINAL )
* @Lob BLOB, CLOB 매핑
* @Transient 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)

# 기본키 매핑 방법
* @Id
  * 직접 할당: @Id만 사용 
  * 자동 ( @GeneratedValue )
    * IDENTITY : 데이터베이스에 위임,MYSQL
      * 기본 키 생성을 데이터베이스에 위임
      * 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용
      * (예: MySQL의 AUTO_ INCREMENT)
      * JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
      * AUTO_ INCREMENT는 데이터베이스에 INSERT SQL을 실행 한 이후에 ID 값을 알 수 있음
      * IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행 하고 DB에서 식별자를 조회
    * SEQUENCE : 데이터 베이스 스퀀스 오브젝스 사용, ORACLE
      * @SequenceGenerator 필요
       * name : 식별자 생성기 이름 필수
       * sequenceName : 데이터베이스에 등록되어 있는 시퀀스 이름 hibernate_sequence
       * initialValue : DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는 수를 지정한다. 1
       * allocationSize : 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨 데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값 을 반드시 1로 설정해야 한다 50
       * catalog, schema 데이터베이스 catalog, schema 이름
    * Table: 키 생성용 테이블 사용, 모든 DB 에서 상용
      * TableGenerator 필요
      * 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
        * 모든데이터베이스에 적용 가능, 성능이 단점
        * name 식별자 생성기 이름 필수
        *  table 키생성 테이블명 hibernate_sequences
        *  pkColumnName 시퀀스 컬럼명 sequence_name
        *  valueColumnName
        *  시퀀스 값 컬럼명 next_val
        *  pkColumnValue 키로 사용할 값 이름 엔티티 이름
        *  initialValue 초기 값, 마지막으로 생성된 값이 기준이다. 0
        *  allocationSize 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨) 50
        *  catalog, schema 데이터베이스 catalog, schema 이름
        *  uniqueConstraints(DDL)
        *  유니크 제약 조건을 지정할 수 있다.
      * AUTO: 방언에 따라 자용 지정, 기본값

@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

* 권장하는 식별자 전략
  *기본 키 제약 조건: null 아님, 유일, 변하면 안된다.
  * 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
  * 예를 들어 주민등록번호도 기본 키로 적절하기 않다.
    * 권장: Long형 + 대체키 + 키 생성전략 사용

