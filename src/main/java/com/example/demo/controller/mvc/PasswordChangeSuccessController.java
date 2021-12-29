package com.example.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordChangeSuccessController {

    @GetMapping(value = "/password_change_success")
    public String view(){
        return "passwordChangeSuccess";
    }
}
