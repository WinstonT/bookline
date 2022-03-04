package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyOrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/profile/orders")
    public String getOrders(Model model){
        try{
            model.addAttribute("auth", Session.getSession());
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
        model.addAttribute("orders", orderService.getPendingOrdersByCustomer(Session.getSession().getId()));
        return "myOrders";
    }
}
