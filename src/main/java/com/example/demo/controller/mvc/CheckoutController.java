package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.EmailSender;
import com.example.demo.controller.utils.Session;
import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import com.example.demo.services.BookService;
import com.example.demo.services.OrderService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BookService bookService;

    @Autowired
    private EmailSender emailSender;

    @GetMapping(value = "/checkout")
    public String checkout(Model model){
        try {
            List<Cart> cartList = bookService.getCartList(Session.getSession());
            model.addAttribute("cartList", cartList);
            model.addAttribute("totalPrice", orderService.getOrderTotalPrice(cartList));
            model.addAttribute("order", new Order());
            model.addAttribute("error", false);
            model.addAttribute("user", Session.getSession());
        }
        catch (NullPointerException e){
            return "redirect:/error";
        }
        return "checkout";
    }

    @PostMapping(value = "/checkout")
    public String checkout(@ModelAttribute("order") Order order, Model model){
        if(order.getShippingAddress().isEmpty()){
            model.addAttribute("error", true);
            return "checkout";
        }
        List<Cart> cartList = bookService.getCartList(Session.getSession());
        order.setUserId(Session.getSession().getId());
        order.setOrderStatus("Pending confirmation");
        order.setTransactionDate(LocalDateTime.now());
        order.setCartItems(cartList);
        order.setTotalPrice(orderService.getOrderTotalPrice(cartList));
        orderService.createOrder(order);
        for (Cart cart: cartList){
            userService.deleteCartItem(Session.getSession(), cart.getBook().getBookTitle());
        }
        emailSender.sendOrderMail(Session.getSession().getUserEmail(), cartList, order);
        return "redirect:/order_success";
    }
}
