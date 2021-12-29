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
public class ViewCartController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/cart")
    public String getCart(Model model){
        try{
            if(Session.getSession().getCartList().isEmpty()){
                model.addAttribute("message", "Your cart is empty.");
            }
            else{
                List<Cart> cartList = bookService.getCartList(Session.getSession());
                if(cartList == null){
                    model.addAttribute("message", "Your cart is empty.");
                    return "viewCart";
                }
                model.addAttribute("cartList", cartList);
                model.addAttribute("totalPrice", bookService.getTotalPrice(cartList));
            }
            return "viewCart";
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
    }

    @PostMapping(value = "/cart")
    public String getCart(@ModelAttribute("cartList") List<Cart> cartList){
        System.out.println("//" + cartList);
        return "checkout";
    }

}
