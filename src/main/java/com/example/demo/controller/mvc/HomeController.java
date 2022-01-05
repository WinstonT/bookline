package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.BookRecommendHelper;
import com.example.demo.controller.utils.Session;
import com.example.demo.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookRecommendHelper bookRecommendHelper;

    @GetMapping(value = {"/index", "/", "", "/home"})
    public String home(Model model){
        List<Book> books = new ArrayList<>();
        model.addAttribute("auth", Session.getSession());
        model.addAttribute("booksForYou", bookRecommendHelper.getRecommendedBooks());
        model.addAttribute("bestSeller", bookRecommendHelper.getBestSellerBook());
        model.addAttribute("recentlyAdded", bookRecommendHelper.getRecentlyAddedBooks());
        model.addAttribute("search", null);
        model.addAttribute("fiction", bookRecommendHelper.getPopularCategoryBook("Fiction"));
        model.addAttribute("poetry", bookRecommendHelper.getPopularCategoryBook("Poetry"));
        model.addAttribute("cooking", bookRecommendHelper.getPopularCategoryBook("Cooking"));
        model.addAttribute("philosophy", bookRecommendHelper.getPopularCategoryBook("Philosophy"));
        model.addAttribute("comic", bookRecommendHelper.getPopularCategoryBook("Comics & Graphic Novels"));
        return "index";
    }
}

/*
*   Best sellers
*   Books under 150k
*   Fiction
* */