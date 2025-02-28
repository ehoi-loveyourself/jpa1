package jpabook.jpashop.service;

import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.domain.items.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    // 저장
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 목록 조회
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    // 단건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
    // 이렇게 리포지토리에게 위임하는 서비스 코드는 컨트롤러에서 바로 리포지토리를 불러도 괜찮다고 생각한다.

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.changeValue(name, price, stockQuantity);
    }
}