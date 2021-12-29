package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.BookValidator;
import com.example.demo.models.Book;
import com.example.demo.models.Message;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class AddNewBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookValidator bookValidator;

    @GetMapping(value = "/admin/books/add")
    public String addNewBook(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("error", null);
        return "addNewBook";
    }

    @PostMapping(value = "/admin/books/add")
    public String addNewBook(@ModelAttribute("book") Book book, Model model){
        Message message = bookValidator.validateBook(book);
        if(message.getPosition() != 0){
            model.addAttribute("error", message.getText());
            model.addAttribute("position", message.getPosition());
            return "addNewBook";
        }
        else{
            List<Book> books = new ArrayList<>();
            books.add(book);
            bookService.createBookIndexBulk(books);
            return "redirect:/admin/books";
        }

    }
}
