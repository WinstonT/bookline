package com.example.demo.controller.exceptions;

import com.example.demo.controller.utils.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PermissionHandlerController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(Session.getSession() == null || !Session.getSession().getUserRole().equals("admin")) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}