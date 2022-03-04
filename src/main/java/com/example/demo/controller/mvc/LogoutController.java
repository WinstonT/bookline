package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(){
        Session.getSession().setAuthenticated(false);
        Session.setSession(null);
        return "redirect:/login";
    }
}
