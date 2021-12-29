package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.AESEncryption;
import com.example.demo.controller.utils.Session;
import com.example.demo.models.ChangePassword;
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
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private AESEncryption aesEncryption;

    @GetMapping(value = "/change_password")
    public String changePassword(Model model){
        model.addAttribute("changePassword", new ChangePassword());
        return "changePassword";
    }

    @PostMapping(value = "/change_password")
    public String changePassword(Model model, @ModelAttribute("changePassword") ChangePassword cp){
        User user = Session.getSession();
        if(!cp.getOldPassword().equals(aesEncryption.decrypt(user.getPassword(), "secret_key"))){
            model.addAttribute("message", new Message("Incorrect password", 1));
        }
        else if(cp.getNewPassword().isEmpty()){
            model.addAttribute("message", new Message("Password is empty", 1));
        }
        else if(!cp.getConfirmPassword().equals(cp.getNewPassword())){
            model.addAttribute("message", new Message("New password and confirm password does not match", 1));
        }
        else{
            userService.updateUserDetails(user, "password", aesEncryption.encrypt(cp.getNewPassword(), "secret_key"));
            Session.setSession(user);
            return "redirect:/profile/my_account";
        }
        return "changePassword";
    }
}
