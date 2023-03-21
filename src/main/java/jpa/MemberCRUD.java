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


            // 수정
            member.setName(name);




            // 커밋 entity에서 데이터의 변화를 감지하고 commit 전에 update 쿼리를 만들어서 알아서 던지고 commit를 함
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

}
