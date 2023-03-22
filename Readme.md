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

