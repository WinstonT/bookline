package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Review;
import com.example.demo.services.BookService;
import com.example.demo.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class WriteReviewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ReviewService reviewService;

    String title;

    @GetMapping(value = "/books/{title}/review")
    public String writeReview(@ModelAttribute("title") String bookTitle, Model model){
        title = bookTitle;
        model.addAttribute("book", bookService.findBook(bookTitle).get(0));
        model.addAttribute("review", new Review());
        return "writeReview";
    }

    @PostMapping(value = "/books/review")
    public String writeReview(@ModelAttribute("review") Review review){
        review.setUserName(Session.session.getUserName());
        review.setBookId(bookService.findBook(title).get(0).getId());
        review.setReviewDate(LocalDate.now());
        reviewService.saveReview(review);
        return "redirect:/history";
    }
}
