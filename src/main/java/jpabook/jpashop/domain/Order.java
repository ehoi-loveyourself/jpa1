package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    private Member member;

    @OneToMany
    private List<OrderItem> orderItems;

    @OneToOne
    private Delivery delivery;

    private LocalDate orderDate;

    private OrderStatus status;
}