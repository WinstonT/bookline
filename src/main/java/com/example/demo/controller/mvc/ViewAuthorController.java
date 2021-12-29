package com.example.demo.controller.mvc;

import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ViewAuthorController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books/author/{author}")
    public String viewAuthor(@ModelAttribute("author") String author, Model model){
        model.addAttribute("author", author);
        model.addAttribute("books", bookService.findBookByAuthor(author));
        return "viewAuthor";
    }
}
