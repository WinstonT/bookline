package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/index", "/", "", "/home"})
    public String home(Model model){
        model.addAttribute("auth", Session.getSession());
        return "index";
    }
}
