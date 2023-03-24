package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Member2CRUD {

    public void findMember2(long id){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        // 디비작업.. 일관적인 작업 ( 트랜젝션 )을 할 때는 꼭 EntityManager를 만들어야 한다.

        // 트랜젝션을 가져온다
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            tx.commit(); // commit를 해도 select는 실행되지만 detach로 비영속 상태가 되면서 update는 실행되지 않는다.
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();

    }
}
