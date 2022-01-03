package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
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
            @RequestParam(value = "search", required = false, defaultValue = "") String query,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "sort", required = false, defaultValue = "rating") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
            Model model){
        int pageNumber = 1;
        String attr = "";
        if(page != null){
            pageNumber = Integer.parseInt(page);
        }
        Map<String, List<Book>> map = paginationService.getPage(query, sort, order, pageNumber);
        List<Book> bookList = new ArrayList<>();
        for(Map.Entry<String, List<Book>> entry: map.entrySet()){
            attr = entry.getKey();
            bookList = new ArrayList<>(entry.getValue());
        }
        String[] split = attr.split("_", 3);
        model.addAttribute("books", bookList);
        model.addAttribute("lastPage", Integer.parseInt(split[1]));
        model.addAttribute("page", pageNumber);
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("search", query);
        model.addAttribute("attr", Integer.parseInt(split[0]));
        model.addAttribute("totalResults", Integer.parseInt(split[2]));
        model.addAttribute("auth", Session.getSession());
        return "browseBooks";
    }
}
