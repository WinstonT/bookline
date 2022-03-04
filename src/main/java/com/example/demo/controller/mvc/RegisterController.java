package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.Session;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/register")
    public String register(Model model){
        if(Session.getSession() != null && Session.getSession().isAuthenticated()){
            return "redirect:/home";
        }
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("message", null);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("user") User user, @ModelAttribute("dateOfBirth") String date, @ModelAttribute("confirmPassword") String confirmPassword, Model model){
        List<User> userResult = userService.findUserByEmail(user.getUserEmail());
        if(user.getUserName().isEmpty()){
            model.addAttribute("message", "Name is not filled");
        }
        else if(user.getUserEmail().isEmpty()){
            model.addAttribute("message", "Email not filled");
        }
        else if(userResult.size() > 0){
            model.addAttribute("message", "Email already in use");
        }
        else if(user.getPassword().isEmpty()){
            model.addAttribute("message", "Password not filled");
        }
        else if(confirmPassword.isEmpty()){
            model.addAttribute("message", "Confirm password not filled");
        }
        else if(!user.getPassword().equals(confirmPassword)){
            model.addAttribute("message", "Passwords does not match");
        }
        else if(date.isEmpty()){
            model.addAttribute("message", "Date of birth not filled");
        }
        else{
            User newUser = User.builder()
                    .userName(user.getUserName())
                    .userEmail(user.getUserEmail())
                    .password(user.getPassword())
                    .userRole("user")
                    .isAuthenticated(true)
                    .userDateOfBirth(LocalDate.parse(date))
                    .build();
            userService.createUserIndex(newUser);
            return "redirect:/index";
        }
        model.addAttribute("user", user);
        return "register";
    }
}
