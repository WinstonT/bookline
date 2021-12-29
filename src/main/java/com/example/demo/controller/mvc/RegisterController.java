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

import java.time.LocalDate;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    Message message = new Message("", 0);

    @GetMapping(value = "/register")
    public String register(Model model){
        if(Session.getSession() != null && Session.getSession().isAuthenticated()){
            return "redirect:/home";
        }
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("message", message);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("user") User user, @ModelAttribute("dateOfBirth") String date, Model model){
        List<User> userResult = userService.findUserByEmail(user.getUserEmail());
        if(userResult == null || userResult.isEmpty()){
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
        else{
            message.setText("Email already in use");
            message.setPosition(1);
            model.addAttribute("message", message);
            return "register";
        }
    }
}
