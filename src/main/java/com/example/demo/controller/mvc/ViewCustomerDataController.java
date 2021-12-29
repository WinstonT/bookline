package com.example.demo.controller.mvc;

import com.example.demo.services.OrderService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ViewCustomerDataController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/admin/customer/{id}")
    public String viewCustomerData(@ModelAttribute("id") String userId, Model model){
        try{
            model.addAttribute("user", userService.findUserById(userId));
            model.addAttribute("orderList", orderService.getOrdersByCustomer(userId));
            return "viewCustomerData";
        }
        catch (NullPointerException e){
            return "redirect:/";
        }
    }
}
