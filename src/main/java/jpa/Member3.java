package jpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "NUMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1
)
@TableGenerator(
        name = "Number_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnName = "MEMBER_SEQ", allocationSize = 50
)
public class Member3 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR  ")
    private String id;

    @Column(name = "name")
    private String username;


}

//
//    create table MY_SEQUENCES (
//        sequence_name varchar(255) not null,
//        next_val bigint,
//        primary key ( sequence_name )
//        )
//@Entity