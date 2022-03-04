package com.example.demo.controller.mvc;

import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ViewBookDataController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/admin/books/view/{title}")
    public String viewBook(@ModelAttribute("title") String bookTitle, Model model){
        Book book = bookService.findBook(bookTitle).get(0);
        model.addAttribute("sold", orderService.getNumberOfBooksSold(book.getId()));
        model.addAttribute("book", book);
        model.addAttribute("reviewList", reviewService.getReviewByBook(book.getId()));
        model.addAttribute("avgScore", reviewService.getAverageScore(book.getId()));
        return "viewBookData";
    }
}
