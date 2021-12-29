package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.Message;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangeEmailController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/change_email")
    public String changeEmail(Model model){
        model.addAttribute("user", new User());
        return "changeEmail";
    }

    @PostMapping(value = "/change_email")
    public String changeEmail(@ModelAttribute("user") User updateEmail, Model model){
        if(updateEmail.getUserEmail().isEmpty()){
            model.addAttribute("message", new Message("New email is empty", 1));
            return "changeEmail";
        }
        User user = Session.getSession();
        userService.updateUserDetails(user, "userEmail", updateEmail.getUserEmail());
        Session.setSession(userService.findUserByEmail(updateEmail.getUserEmail()).get(0));
        System.out.println(Session.getSession());
        return "redirect:/profile/my_account";
    }
}
