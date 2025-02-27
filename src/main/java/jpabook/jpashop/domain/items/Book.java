package jpabook.jpashop.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book extends Item {

    private String author;
    private String isbn;

    private Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn) {
        return new Book(name, price, stockQuantity, author, isbn);
    }
}