package com.example.demo.controller.utils;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomStringGenerator {

    public static String generateString(int length){
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString = new String(array, Charset.forName("UTF-8"));
        StringBuffer r = new StringBuffer();
        for (int k = 0; k < randomString.length(); k++){
            char ch = randomString.charAt(k);
            if(((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (length > 0)){
                r.append(ch);
                length--;
            }
        }
        return r.toString();
    }
}
