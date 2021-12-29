package com.example.demo.controller.mvc;

import com.example.demo.services.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewAllBooksController {

    @Autowired
    private PaginationService paginationService;

    @GetMapping(value = "/admin/books")
    public String viewBooks(Model model){
        model.addAttribute("books", paginationService.getPage("", "title", "asc", 1));
        return "viewAllBooks";
    }

    @GetMapping(value = "/admin/books/{page}")
    public String viewAllBooks(@PathVariable("page") int page, Model model){
        model.addAttribute("books", paginationService.getPage("", "title", "asc", page));
        return "viewAllBooks";
    }
}
