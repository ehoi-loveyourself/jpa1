package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // 읽기 전용이라는 뜻, 나는 Order 엔티티에 있는 member 필드로 엮인 애라는 뜻?!
    private List<Order> orders = new ArrayList<>();
}