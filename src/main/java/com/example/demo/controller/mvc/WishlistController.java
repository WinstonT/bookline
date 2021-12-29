package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class WishlistController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/wishlist")
    public String getWishlist(Model model){
        if(Session.getSession() == null || Session.getSession().getWishlist().isEmpty()){
            model.addAttribute("message", "Your wishlist is empty.");
            return "wishlist";
        }
        List<Book> books = new ArrayList<>();
        for(String title: Session.getSession().getWishlist()){
            books.add(bookService.findBook(title).get(0));
        }
        model.addAttribute("books", books);
        return "wishlist";
    }

    @GetMapping(value = "/wishlist/delete/{title}")
    public String removeFromWishlist(@ModelAttribute("title") String title){
        userService.deleteBookFromWishlist(Session.getSession(), title);
        return "redirect:/wishlist";
    }
}
