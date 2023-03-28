package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ORDERS")
public class Orders {
    @Id @GeneratedValue
    private long id;
    private long memberId;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
