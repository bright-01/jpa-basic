package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class FlushEx {

    public void flushExTest(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Member1 member = new Member1(200L, "member200");
            em.persist(member);

            // flush를 하는 순간에 데이터 저장
            // 쿼리를 미리 보고 싶거나 미리 데이터를 넣고 싶다면...
            em.flush();
            // ? flush 를 하면 1차 캐시 지워지지 않음.
            // 쓰기 지연 sql을 미리 실행해서 데이터베이스에 쌓인다


            System.out.println("========= 이 선 전에 쿼리가 발생함");


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
