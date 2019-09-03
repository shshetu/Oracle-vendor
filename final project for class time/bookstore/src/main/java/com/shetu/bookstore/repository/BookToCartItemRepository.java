package com.shetu.bookstore.repository;

import com.shetu.bookstore.domain.BookToCartItem;
import com.shetu.bookstore.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BookToCartItemRepository extends JpaRepository<BookToCartItem,Long> {
    void deleteByCartItem(CartItem cartItem);
}
