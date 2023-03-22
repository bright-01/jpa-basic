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
