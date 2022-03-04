package com.example.demo.controller.utils;

import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetMail(String emailTo, String password) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("bookline.help@gmail.com");
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

    public void sendOrderMail(String emailTo, List<Cart> cartItems, Order order) {
        String items = "";
        for(Cart cart: cartItems){
            items = items + cart.getBook().getBookTitle() + " x" + cart.getQuantity() + "\n";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        String date = order.getTransactionDate().format(dateTimeFormatter);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("bookline.help@gmail.com");
        mailMessage.setTo(emailTo);
        mailMessage.setSubject("Order Checkout");
        mailMessage.setText(
                        "Thank you for your purchase\n" +
                        "Order ID: " + order.getId() +"\n" +
                        "Purchase Date: " + date + "\n" +
                        items + "\n" +
                        "\n\n\n\n" +
                        "From,\n" +
                        "Bookline Support Team"
        );
        mailSender.send(mailMessage);
    }
}
