package com.example.demo.services;

import com.example.demo.controller.utils.AESEncryption;
import com.example.demo.controller.utils.TimeHelper;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AESEncryption aesEncryption;

    @Autowired
    private RestHighLevelClient client;

    public User findUserById(String id){
        List<User> userList = findAllUsers();
        for(User user: userList){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public List<User> findAllUsers(){
        return userRepository.findAllBy();
    }

    public List<User> findUserByEmail(String email){
        return userRepository.findUserByUserEmail(email);
    }

    public void createUserIndex(User user){
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        User newUser = User.builder()
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .password(aesEncryption.encrypt(user.getPassword(), "secret_key"))
                .userRole(user.getUserRole())
                .isAuthenticated(user.isAuthenticated())
                .userDateOfBirth(user.getUserDateOfBirth())
                .wishlist(list)
                .cartList(list2)
                .dateRegistered(new TimeHelper().getDateNow())
                .build();

        userRepository.save(newUser);
    }

    public void deleteUser(String id){
        userRepository.deleteById(id);
    }

    public User findSessionUser(){
        for(User user: findAllUsers()){
            if(user.isAuthenticated()){
                System.out.println(user);
                return user;
            }
        }
        return null;
    }

    public void updateStringList(User user, List<String> list, String field){
        UpdateRequest updateRequest = new UpdateRequest("user", user.getId()).doc(field, list);
        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        RefreshRequest request = new RefreshRequest("user");
        try {
            client.indices().refresh(request, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void addBookToWishlist(User user, String bookTitle){
        List<String> wishlist = user.getWishlist();
        wishlist.add(bookTitle);
        updateStringList(user, wishlist, "userWishlist");
    }

    public boolean checkBookExistInWishlist(User user, String bookTitle){
        List<String> wishlist = user.getWishlist();
        for(String s: wishlist){
            if(s.equals(bookTitle)){
                return true;
            }
        }
        return false;
    }

    public void deleteBookFromWishlist(User user, String bookTitle){
        List<String> wishlist = user.getWishlist();
        for(String s: wishlist){
            if(s.equals(bookTitle)){
                wishlist.remove(s);
                break;
            }
        }
        updateStringList(user, wishlist, "userWishlist");
    }

    public int checkIfBookExistInCart(User user, String bookTitle){
        try{
            for(String s: user.getCartList()){
                if(s.contains(bookTitle)){
                    String[] str = s.split("_", 2);
                    return Integer.parseInt(str[1]);
                }
            }
            return 0;
        }
        catch (NullPointerException e){
            return 0;
        }
    }

    public void addBookToCart(User user, String bookTitle){
        List<String> cartList = user.getCartList();
        cartList.add(bookTitle + "_1");
        updateStringList(user, cartList, "cartList");
    }

    public void changeCartItemQuantity(User user, String bookTitle, int newQuantity){
        if(newQuantity == 0){
            deleteCartItem(user, bookTitle);
        }
        List<String> cartList = user.getCartList();
        for(String s: cartList){
            if(s.contains(bookTitle)){
                cartList.set(cartList.indexOf(s), bookTitle + "_" + newQuantity);
            }
        }
        updateStringList(user, cartList, "cartList");
    }

    public void deleteCartItem(User user, String bookTitle){
        List<String> cartList = user.getCartList();
        for (String s: cartList){
            if(s.contains(bookTitle)){
                cartList.remove(s);
                break;
            }
        }
        updateStringList(user, cartList, "cartList");
    }

    public void updateUserDetails(User user, String field, String value){
        UpdateRequest updateRequest = new UpdateRequest("user", user.getId()).doc(field, value);
        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        RefreshRequest request = new RefreshRequest("user");
        try {
            client.indices().refresh(request, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
