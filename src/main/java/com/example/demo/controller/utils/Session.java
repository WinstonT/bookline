package com.example.demo.controller.utils;

import com.example.demo.models.User;
import org.springframework.stereotype.Component;

@Component
public class Session {

    public static User session;

    public static User getSession() {
        return session;
    }

    public static void setSession(User session) {
        Session.session = session;
    }
}
