package jpabook.jpashop.controller;

import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.domain.items.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemRepository itemRepository;

    public ItemController(ItemService itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @GetMapping("/new")
    public String createItemForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/new")
    public String createItem(BookForm form) {
        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping("")
    public String itemList(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/itemsList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {
        Book findItem = (Book) itemRepository.findOne(itemId);

        // 엔티티를 직접 옮기는 것이 아니라 dto를 생성해서 넘기기
        BookForm form = new BookForm();
        form.setName(findItem.getName());
        form.setPrice(findItem.getPrice());
        form.setStockQuantity(findItem.getStockQuantity());
        form.setAuthor(findItem.getAuthor());
        form.setIsbn(findItem.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, BookForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}