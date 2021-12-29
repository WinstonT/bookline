package com.example.demo.controller.mvc;

import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/admin/order")
    public String order(Model model){
        List<Order> orderList = orderService.getAllOrders().stream().sorted(Comparator.comparing(Order::getTransactionDate).reversed()).collect(Collectors.toList());
        model.addAttribute("orderList", orderList);
        model.addAttribute("query", new Order());
        return "adminOrder";
    }

    @PostMapping(value = "/admin/order")
    public String searchOrder(@ModelAttribute("query") Order query, Model model){
        String id = query.getId();
        if(id.isEmpty() || id == null){
            order(model);
        }
        Order order = orderService.findOrderById(id);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        model.addAttribute("orderList", orderList);
        model.addAttribute("query", new Order());
        return "adminOrder";
    }
}
