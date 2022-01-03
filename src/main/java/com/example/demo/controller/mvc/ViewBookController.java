package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Book;
import com.example.demo.models.Cart;
import com.example.demo.models.Review;
import com.example.demo.services.BookService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ReviewService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ViewBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/books/{title}")
    public String viewBook(Model model, @PathVariable("title") String bookTitle){
        Book book = bookService.findBook(bookTitle).get(0);
        model.addAttribute("book", book);
        model.addAttribute("cart", new Cart());
        List<Review> reviewList = reviewService.getReviewByBook(book.getId());
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("avgScore", reviewService.getAverageScore(book.getId()));
        model.addAttribute("sold", orderService.getNumberOfBooksSold(book.getId()));
        try{
            model.addAttribute("bookInCart", userService.checkIfBookExistInCart(Session.getSession(), bookTitle));
            model.addAttribute("bookInWishlist", userService.checkBookExistInWishlist(Session.getSession(), bookTitle));
        }
        catch (NullPointerException e){
            model.addAttribute("bookInCart", 0);
            model.addAttribute("bookInWishlist", false);
        }
        return "viewBook";
    }

    @GetMapping(value = "/books/cart/add/{title}")
    public String insertBookToCart(@ModelAttribute("title") String title, Model model){
        int quantity;
        try{
            quantity = userService.checkIfBookExistInCart(Session.getSession(), title);
            if(quantity > 0){
                userService.deleteCartItem(Session.getSession(), title);
            }
            else userService.addBookToCart(Session.getSession(), title);
            return "redirect:/books/" + title;
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
    }

    @GetMapping(value = "/wishlist/add/{title}")
    public String insertBookToWishlist(@ModelAttribute("title") String title){
        try{
            if(!userService.checkBookExistInWishlist(Session.getSession(), title)){
                userService.addBookToWishlist(Session.getSession(), title);
            }
            else userService.deleteBookFromWishlist(Session.getSession(), title);
            return "redirect:/books/" + title;
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
    }
}
