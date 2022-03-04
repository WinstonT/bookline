package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import com.example.demo.services.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ViewAllBooksController {

    @Autowired
    private PaginationService paginationService;

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/admin/books")
    public String getAllBooks(
            Model model,
            HttpServletRequest request,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "title") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "1") String page){
        Map<String, List<Book>> map = paginationService.getPage(search, sort, order, Integer.parseInt(page));
        List<Book> bookList = new ArrayList<>();
        for(Map.Entry<String, List<Book>> entry: map.entrySet()){
            bookList = new ArrayList<>(entry.getValue());
        }
        model.addAttribute("books", bookList);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("page", Integer.parseInt(page));
        model.addAttribute("lastPage", Integer.parseInt(paginationService.getLastPageIndex(bookService.getAllBooks())));
        Session.setLastPage(request.getRequestURL().toString() + "?" + request.getQueryString());
        return "viewAllBooks";
    }
}
