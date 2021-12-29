package com.example.demo.controller.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import java.nio.charset.Charset;
import java.util.Random;

@Controller
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String emailTo, String password) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("noreply@bookline.com");
        mailMessage.setTo(emailTo);
        mailMessage.setSubject("Password reset");
        mailMessage.setText(
                        "Hello, " + emailTo + "\n" +
                        "You are receiving this email because you requested a password reset.\n" +
                        "Your new password: " + password + "\n\n" +
                        "From, " + "\n" +
                        "Bookline Support Team");

        mailSender.send(mailMessage);
    }
}
