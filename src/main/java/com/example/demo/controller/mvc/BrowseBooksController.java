package com.example.demo.controller.mvc;

import com.example.demo.models.Book;
import com.example.demo.services.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class BrowseBooksController {

    @Autowired
    private PaginationService paginationService;

    @RequestMapping(value = "/books")
    public String getSortedBooksPage(
            @RequestParam(value = "search", required = false) String query,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            Model model){
        int pageNumber = 1;
        int attr = 1;
        if(page != null){
            pageNumber = Integer.parseInt(page);
        }
        Map<Integer, List<Book>> map = paginationService.getPage(query, sort, order, pageNumber);
        List<Book> bookList = new ArrayList<>();
        for(Map.Entry<Integer, List<Book>> entry: map.entrySet()){
            attr = entry.getKey();
            bookList = new ArrayList<>(entry.getValue());
        }
        if(query == null){
            query = "";
        }
        if(sort == null){
            sort = "rating";
        }
        if(order == null){
            order = "desc";
        }
        model.addAttribute("books", bookList);
        model.addAttribute("lastPage", paginationService.getLastPageIndex(bookList));
        model.addAttribute("page", pageNumber);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("search", query);
        model.addAttribute("attr", attr);
        return "browseBooks";
    }
}
