package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.EmailSender;
import com.example.demo.controller.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AboutController {

    @Autowired
    private EmailSender emailSender;

    @GetMapping(value = "/about")
    public String viewAbout(Model model){
        model.addAttribute("auth", Session.getSession());
        Session.setLastPage("/about");
        return "about";
    }

    @PostMapping(value = "/feedback")
    public String giveFeedback(@ModelAttribute("message") String message){
        try{
            emailSender.sendFeedbackMail(Session.getSession().getUserEmail(), message);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        finally {
            return "redirect:/about";
        }
    }
}
