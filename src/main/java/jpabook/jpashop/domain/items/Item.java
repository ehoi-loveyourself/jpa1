package jpabook.jpashop.domain.items;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    protected Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //==비즈니스 로직==

    /**
     * 재고 수량 증가 로직
     * @param quantity
     */
    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void decreaseStockQuantity(int quantity) {
        int remain = this.stockQuantity - quantity;
        if (remain < 0) {
            throw new NotEnoughStockException("Stock Quantity should not be minus");
        }

        this.stockQuantity = remain;
    }
}