package com.shetu.adminportal.service;

import com.shetu.adminportal.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService  {
    Book save(Book book);
    List<Book> findAll();
    //find by id
    Book findBookByBookId(Long id);
}
