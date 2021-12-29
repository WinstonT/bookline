package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Cart;
import com.example.demo.services.BookService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChangeQuantityController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/cart/change_quantity/{title}")
    public String changeQuantity(@ModelAttribute("title") String title, Model model){
        List<String> cartList = Session.getSession().getCartList();
        for(String s: cartList){
            if(s.contains(title)){
                String[] arr = s.split("_", 2);
                Cart cartItem = Cart.builder()
                        .book(bookService.findBook(title).get(0))
                        .quantity(Integer.parseInt(arr[1]))
                        .build();
                model.addAttribute("cart", cartItem);
                return "changeQuantity";
            }
        }
        model.addAttribute("error", "Book not found");
        return "changeQuantity";
    }

    @PostMapping(value = "/cart/change_quantity")
    public String changeQuantity(@ModelAttribute("cart") Cart cart, Model model) {
        userService.changeCartItemQuantity(Session.getSession(), cart.getBook().getBookTitle(), cart.getQuantity());
        return "redirect:/cart";
    }
}
