package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.AESEncryption;
import com.example.demo.controller.utils.Session;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AESEncryption aesEncryption;

    @GetMapping(value = "/login")
    public String login(Model model){
        if(Session.getSession() != null && Session.getSession().isAuthenticated()){
            return "redirect:/home";
        }
        model.addAttribute("user", new User());
        model.addAttribute("message", "");
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute("user") User user, Model model){
        User verifiedUser = null;
        if(user.getUserEmail().isEmpty()){
            model.addAttribute("message", "Email not filled");
        }
        else if(user.getPassword().isEmpty()){
            model.addAttribute("message", "Password not filled");
        }
        else{
            try {
                verifiedUser = userService.findUserByEmail(user.getUserEmail()).get(0);
            }
            catch (IndexOutOfBoundsException e){
                model.addAttribute("message", "Email not registered");
                return "login";
            }
            String decoded = aesEncryption.decrypt(verifiedUser.getPassword(), "secret_key");
            if(user.getPassword().equals(decoded)){
                Session.setSession(verifiedUser);
                Session.getSession().setAuthenticated(true);
                if(Session.getSession().getUserRole().equals("admin")){
                    return "redirect:/admin/dashboard";
                }
                return "redirect:/home";
            }
            else{
                model.addAttribute("message", "Incorrect password");
            }

        }
        return "login";
    }

}
