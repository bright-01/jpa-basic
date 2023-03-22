package jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="member") // name="" 을 통해서 매핑 테이블을 수동으로 정할 수 있다.. 없으면 클래스 이름으로 테이블이 매핑
public class Member {

    @Id
    @Column(unique = true, length = 10)
    private long id;
    @Column(name="name") //@Column(name="name") 으로 테이블의 컬럼을 매핑할 수 도 있음.. 없으면 변수명으로 컬럼 매핑
    private String name;

    // JPA에서는 기본 생성자가 있어야 한다
    public Member() {}

    public Member(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
