package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.items.Item;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Setter
@Getter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    /* 주문 상품 생성 */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.decreaseStockQuantity(count);

        return orderItem;
    }

    /* 주문 취소 */
    public void cancel() {
        item.addStockQuantity(this.count);
    }

    /* 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return this.orderPrice * this.count;
    }
 }