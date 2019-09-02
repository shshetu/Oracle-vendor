package com.shetu.bookstore.service;

import com.shetu.bookstore.domain.Book;
import com.shetu.bookstore.domain.CartItem;
import com.shetu.bookstore.domain.ShoppingCart;
import com.shetu.bookstore.domain.User;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    CartItem updateCartItem(CartItem cartItem);
    CartItem addBookToCartItem(Book book, User user, int qty);
    CartItem findById(Long id);
    void removeCartItem(CartItem cartItem);
    CartItem save(CartItem cartItem);

}
