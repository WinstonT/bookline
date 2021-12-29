package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Controller
public class MyAccountController {

    @GetMapping(value = "/profile/my_account")
    public String displayMyAccount(Model model){
        try{
            model.addAttribute("user", Session.getSession());
            model.addAttribute("dateOfBirth", Session.getSession().getUserDateOfBirth().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            model.addAttribute("dateRegistered", Session.getSession().getDateRegistered().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
            return "userProfile";
        }catch (NullPointerException e){
            return "redirect:/login";
        }
    }
}
