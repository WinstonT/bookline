package com.example.demo.controller.mvc;

import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/admin/dashboard")
    public String getDashboard(Model model){
        model.addAttribute("orders", orderService.getPendingOrders());
        return "adminDashboard";
    }
}
