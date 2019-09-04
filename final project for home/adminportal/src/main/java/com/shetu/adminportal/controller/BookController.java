package com.shetu.adminportal.controller;

import com.shetu.adminportal.domain.Book;
import com.shetu.adminportal.service.BookService;
import com.shetu.adminportal.service.impl.BookServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    //Get books
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBook(Model model) {
        //create a new book instance
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";

    }

    //Post books
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book,
                          HttpServletRequest request) {
        //create a book service
        //first save the book so that we can get the book id
        bookService.save(book);
        //find the book image
        MultipartFile bookImage = book.getBookImage();

        try {
            //get the byte from the image
            ///inorder to write the file in the filesystem we have to convert into byte
            byte[] bytes = bookImage.getBytes();
            //get the name
            String name = book.getId() + ".png";
            //Buffered output stream: inside new FileOutputStream(new File(location));
            //we use to write the path of the image
            ///home/shetu/intellij-wokspace/adminportal/src/main/resources/static/image
            //src/main/resources/static/image/book/
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:bookList";

    }

    //BookInfo
    @RequestMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") Long id, Model model) {
        //create an object of Book entity
        Book book = bookService.findBookByBookId(id);
        ///this attribute name : book will be sent to the template page
        model.addAttribute("book", book);
        return "bookInfo";
    }

    //Booklist
    @RequestMapping("/bookList")
    public String bookList(Model model) {
        //use the Service method to call all the values from the database
        List<Book> bookList = bookService.findAll();
        //add that into our model so that we can retrieve it
        model.addAttribute("bookList", bookList);
        return "bookList";
    }

    // Update Book
    @RequestMapping("/updateBook")
    public String updateBook(@RequestParam("id") Long id, Model model) {
        //create a book object using service
        Book book = bookService.findBookByBookId(id);
        //send the object to the thymeleaf page
        model.addAttribute("book", book);
        return "updateBook";
    }

    //update book = post mapping
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST)
    public String updateBookPost(@ModelAttribute("book") Book book
            , HttpServletRequest request) {
        bookService.save(book);

        //book Image
        MultipartFile bookImage = book.getBookImage();

        //logic for image adding
        if (!bookImage.isEmpty()) {
            try {

                //get the byte from the image
                ///inorder to write the file in the filesystem we have to convert into byte
                byte[] bytes = bookImage.getBytes();
                //get the name
                String name = book.getId() + ".png";
                ///delete the image
                Files.delete(Paths.get("src/main/resources/static/image/book/" + name));
                //Buffered output stream: inside new FileOutputStream(new File(location));
                //we use to write the path of the image
                ///home/shetu/intellij-wokspace/adminportal/src/main/resources/static/image
                //src/main/resources/static/image/book/
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/" + name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/book/bookInfo?id=" + book.getId();

    }

    ////////////////////////////remove book
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute("id") String id, Model model) {
        bookService.removeBookById(Long.parseLong(id.substring(8)));
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "redirect:/book/bookList";

    }

}
