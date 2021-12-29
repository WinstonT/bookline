package com.example.demo.controller.mvc;

import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class DeleteBookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/admin/books/delete/{bookIsbn}")
    public String deleteBook(@ModelAttribute("bookIsbn") String title){
        bookService.deleteBook(title);
        return "redirect:/admin/books";
    }

}
