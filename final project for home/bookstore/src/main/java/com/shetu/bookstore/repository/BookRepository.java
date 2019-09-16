package com.shetu.bookstore.repository;

import com.shetu.bookstore.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findBookByCategory(String category);
    List<Book> findByTitleContaining(String title);


}
