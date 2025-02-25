package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    // 주문
    @Test
    void 주문() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 30000, 10);
        int orderCount = 2;

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품시 주문 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 수가 정확해야 한다", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다", 30000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다", 10 - orderCount, book.getStockQuantity());
    }

    // 주문시 재고수량초과
    @Test
    void 주문시_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("소년이온다", 10000, 10);
        int orderCount = 11;

        // when & then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    // 주문 취소: 재고가 다시 증가해야 한다. 주문 상태가 cancel 이어야 한다.
    @Test
    void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Book book = createBook("JPA", 30000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 상태는 CANCEL 이어야 한다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문 취소된 상품은 그만큼 재고가 증가하여야 한다.", 10, book.getStockQuantity());
    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setAuthor("김영한");
        book.setIsbn("1234");
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);

        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("박태이");
        member.setAddress(new Address("서울", "청계로", "00000"));
        em.persist(member);

        return member;
    }
}