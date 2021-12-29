package com.example.demo.controller.mvc;

import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ViewOrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/admin/order/{id}")
    public String viewOrder(@ModelAttribute("id") String orderId, Model model){
        Order order = orderService.findOrderById(orderId);
        System.out.println(order);
        model.addAttribute("order", order);
        model.addAttribute("user", userService.findUserById(order.getUserId()));
        return "viewOrder";
    }

    @GetMapping(value = "/admin/order/confirm/{id}")
    public String confirmOrder(@ModelAttribute("id") String orderId){
        orderService.confirmOrder(orderId);
        return "redirect:/admin/order";
    }
}
