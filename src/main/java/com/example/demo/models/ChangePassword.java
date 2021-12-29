package com.example.demo.models;

import lombok.Data;

@Data
public class ChangePassword {

    String oldPassword;
    String newPassword;
    String confirmPassword;
}
