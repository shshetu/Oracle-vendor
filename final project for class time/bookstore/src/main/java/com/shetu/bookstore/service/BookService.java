package com.shetu.bookstore.service;


import com.shetu.bookstore.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    //Problem
Book findBookByBookId(Long id);

}
