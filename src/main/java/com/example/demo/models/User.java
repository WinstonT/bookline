package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User  {

    @Id
    private String id;

    @Field(type = FieldType.Text, value = "userName")
    private String userName;

    @Field(type = FieldType.Text, value = "userEmail")
    private String userEmail;

    @Field(type = FieldType.Text, value = "password")
    private String password;

    @Field(type = FieldType.Text, value = "userRole")
    private String userRole;

    @Field(type = FieldType.Date, value = "userDateOfBirth")
    private LocalDate userDateOfBirth;

    @Field(value = "cartList")
    private List<String> cartList;

    @Field(value = "userWishlist")
    private List<String> wishlist;

    @Field(type = FieldType.Boolean, value = "isAuthenticated")
    private boolean isAuthenticated;

    @Field(type = FieldType.Date, value = "dateRegistered")
    private LocalDate dateRegistered;

}
