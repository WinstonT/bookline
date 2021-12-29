package com.example.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionSuccessController {

    @GetMapping("/order_success")
    public String success(){
        return "transactionSuccess";
    }
}
