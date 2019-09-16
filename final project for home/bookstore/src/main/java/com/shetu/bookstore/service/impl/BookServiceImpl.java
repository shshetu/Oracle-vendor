package com.shetu.bookstore.service.impl;

import com.shetu.bookstore.domain.Book;
import com.shetu.bookstore.repository.BookRepository;
import com.shetu.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
@Autowired
private BookRepository bookRepository;
    @Override
    public List<Book> findAll() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();
        for (Book book: bookList){
            if(book.isActive()){
                activeBookList.add(book);
            }
        }
        return activeBookList;
    }

    @Override
    public Book findBookByBookId(Long id) {
        //empty Book model
        Book tempBook = new Book();

        //empty Book entity
        Optional<Book> tempBook1 = bookRepository.findById(id);

        //checking data is present or not
        if(tempBook1.isPresent()){
        //getting data from tempBook1 to book entity
            tempBook = tempBook1.get();
        }
        return tempBook;
    }

    @Override
    public List<Book> findByCategory(String category) {
        {
            List<Book> bookList = bookRepository.findBookByCategory(category);
            List<Book> activeBookList = new ArrayList<>();

            for (Book book:bookList){
                if(book.isActive()){
                    activeBookList.add(book);
                }
            }
            return activeBookList;
        }
    }

    @Override
    public List<Book> blurrySearch(String title) {
        List<Book> bookList = bookRepository.findByTitleContaining(title);
        List<Book> activeBookList = new ArrayList<>();
        for (Book book:bookList){
            if(book.isActive()){
                activeBookList.add(book);
            }
        }
        return activeBookList;

    }


}
