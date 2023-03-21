package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class HelloJPA {
    public static void main(String[] args) {
        // 등록
//        new MemberCRUD().createMember(3, "HelloC");

        // 수정
//        new MemberCRUD().updateMember(1, "HelloA-2");

        // 조회
        new MemberCRUD().jpqlSelect();

    }
}
