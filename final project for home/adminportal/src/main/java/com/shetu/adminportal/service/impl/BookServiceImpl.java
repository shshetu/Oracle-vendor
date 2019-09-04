package com.shetu.adminportal.service.impl;

import com.shetu.adminportal.domain.Book;
import com.shetu.adminportal.repository.BookRepository;
import com.shetu.adminportal.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
                public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        //the returned result is an iterable interface
        //but we know that we want the list type data
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book findBookByBookId(Long id) {
        ///create an object of book
        Book book = new Book();
        //OPtional: make an empty book entity
        Optional<Book> tempBook = bookRepository.findById(id);
        //check the data is present or not
        if(tempBook.isPresent()){
            book = tempBook.get();
        }
        //return the object of the book by assigning it by book entity
        return book;
    }

    @Override
    public void removeBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
