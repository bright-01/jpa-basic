package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class MemberCRUD {


    public void createMember(long id, String name) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        // 디비작업.. 일관적인 작업 ( 트랜젝션 )을 할 때는 꼭 EntityManager를 만들어야 한다.

        // 트랜젝션을 가져온다
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setId(id);
            member.setName(name);
            // 저장
            em.persist(member);

            // 커밋
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }

    public void updateMember(long id, String name){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        // 디비작업.. 일관적인 작업 ( 트랜젝션 )을 할 때는 꼭 EntityManager를 만들어야 한다.

        // 트랜젝션을 가져온다
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            //조회
            Member member = em.find(Member.class, id); // 클래스 이름, key
            System.out.println("member.getId() " + member.getId());
            System.out.println("member.getName() " + member.getName());


            // 수정 == > persist 를 호출 하지 않아도 컬렉션 처럼 저장 할 수 있다 -> 디비 업데이트가 된다.
            // JPA는 컬렉션 처럼 사용 할 수 있는 편의성!
            member.setName(name);

            /**
            * 변경 감지 ( Dirty Checking ) : update 시 영속 컨텍스트 내 스냅샷과 비교된 객체를 비교
            * 영속 컨텍스트
            * 1. flush()
            * 2. 엔티티와 스냅샷 비교
            *   @id         Entity    스냅샷
            * "member1"     member1   member1 스냅샷
            * "member2"     member2   member2 스냅샷
            *
            * 3. update sql 생성 ( 쓰기 지연 SQL 저장소 )
            *
            * 4. flush() -> update sql 실행
            * 5. commit
            * */



            // 삭제는
            // Member memberA = em.find(Member.class, "memberA"); // 삭제 대상 엔티티 조회
            // em.remove(memberA) // 엔티티 삭제


            // 커밋 entity에서 데이터의 변화를 감지하고 commit 전에 update 쿼리를 만들어서 알아서 던지고 commit를 함
            // sql은 이부분에서 실행된다.
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }

    public void jpqlSelect(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        // 디비작업.. 일관적인 작업 ( 트랜젝션 )을 할 때는 꼭 EntityManager를 만들어야 한다.

        // 트랜젝션을 가져온다
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            List<Member> memberList = em.createQuery("SELECT m FROM member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : memberList) {
                System.out.println("member = " + member.getName());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }

    public void findMember(long id, String name){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        // 디비작업.. 일관적인 작업 ( 트랜젝션 )을 할 때는 꼭 EntityManager를 만들어야 한다.

        // 트랜젝션을 가져온다
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member member = new Member();
            member.setId(id);
            member.setName("JPATEST");

            System.out.println("=== Before ===");
            em.persist(member);
            System.out.println("=== After ===");


            //조회
            // 트랜젝션을 생성하고 perist로 실행 하면 1차 캐시에 데이터가 저장되고 캐시에서 조회를 하게 된다. 그래서 find 할때 sql이 발생 되지 않는다..
            // 같은 것을 2번 조회 할 때 역시 영속성 컨텍스트에 1차 캐시에 저장 되서 sql을 2번 조회 하지 않음
            Member findMember = em.find(Member.class, id); // 클래스 이름, key
            System.out.println("findMember.getId() " + findMember.getId());
            System.out.println("findMember.getName() " + findMember.getName());


            // 영속 엔티티의 동일성 보장
            Member findMember1 = em.find(Member.class, id); // 클래스 이름, key
            Member findMember2 = em.find(Member.class, id); // 클래스 이름, key
            System.out.println("result = " + (findMember1 == findMember2));


            // 커밋 entity에서 데이터의 변화를 감지하고 commit 전에 update 쿼리를 만들어서 알아서 던지고 commit를 함
            // sql은 이부분에서 실행된다.
            // 지연등록 ==> 버퍼등록으로 인해 성능을 올릴 수 있다.
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }

}
