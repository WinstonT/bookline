package com.example.demo.controller.utils;

import com.example.demo.models.User;
import org.springframework.stereotype.Component;

@Component
public class Session {

    public static User session;

    public static String lastPage;

    public static User getSession() {
        return session;
    }

    public static void setSession(User session) {
        Session.session = session;
    }

    public static String getLastPage() {
        return lastPage;
    }

    public static void setLastPage(String lastPage) {
        Session.lastPage = lastPage;
    }
}
