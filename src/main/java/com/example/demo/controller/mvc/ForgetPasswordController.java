package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.AESEncryption;
import com.example.demo.controller.utils.EmailSender;
import com.example.demo.controller.utils.RandomStringGenerator;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForgetPasswordController {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private AESEncryption aesEncryption;

    @GetMapping(value = "/forgot_password")
    public String forgetPassword(){
        return "forgetPassword";
    }

    @PostMapping(value = "/forgot_password")
    public String forgetPassword(@ModelAttribute("email") String email){
        String newPassword = RandomStringGenerator.generateString(8);
        System.out.println(newPassword);
        try{
            userService.updateUserDetails(userService.findUserByEmail(email).get(0), "password", aesEncryption.encrypt(newPassword, "secret_key"));
            emailSender.sendMail(email, newPassword);
            return "redirect:/password_change_success";
        }
        catch (IndexOutOfBoundsException e){
            return "redirect:/forget_password";
        }
    }
}
