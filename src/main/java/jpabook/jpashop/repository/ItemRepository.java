package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.items.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    /**
     * 등록과 수정에 사용
     * @param item
     * @return
     */
    public void save(Item item) {
        if (item.getId() == null) { // 등록
            em.persist(item);
        } else { // 수정
            em.merge(item); // merge에 대해 뒤에서 설명
        }
    }

    /**
     * 상품 단건 조회
     * @param id
     * @return
     */
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    /**
     * 상품 전체 조회
     * @return
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}