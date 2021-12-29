package com.example.demo.services;

import com.example.demo.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private BookService bookService;

    public List<Cart> formatCart(List<String> cart){
        List<Cart> cartList = new ArrayList<>();
        for(String s: cart){
            String[] split = s.split("_", 2);
            Cart cartItem = Cart.builder()
                    .book(bookService.findBook(split[0]).get(0))
                    .quantity(Integer.parseInt(split[1]))
                    .build();
            cartList.add(cartItem);
        }
        return cartList;
    }
}
