package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @GetMapping(value = "/about")
    public String viewAbout(Model model){
        model.addAttribute("auth", Session.getSession());
        return "about";
    }
}
