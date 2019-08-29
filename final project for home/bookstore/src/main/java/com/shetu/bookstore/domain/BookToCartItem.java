package com.shetu.bookstore.domain;

import javax.persistence.*;

@Entity
public class BookToCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    ///Relation with Book class
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    ///////////Relation with CartItem
    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    //getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
