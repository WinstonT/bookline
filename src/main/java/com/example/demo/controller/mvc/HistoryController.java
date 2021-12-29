package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Order;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HistoryController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/history")
    public String showHistory(Model model){
        if(Session.getSession() == null){
            return "login";
        }
        try{
            List<Order> orderList = orderService.getCompletedOrdersByCustomer(Session.getSession().getId());
            orderList = orderList.stream().sorted(Comparator.comparing(Order::getTransactionDate).reversed()).collect(Collectors.toList());
            model.addAttribute("orderList", orderList);
            return "history";
        }
        catch (NullPointerException e){
            return "redirect:/login";
        }
        catch (TemplateInputException e){
            return "redirect:/login";
        }
    }
}
