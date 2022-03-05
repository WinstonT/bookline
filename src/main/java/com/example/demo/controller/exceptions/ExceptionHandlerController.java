package com.example.demo.controller.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class ExceptionHandlerController implements ErrorController {

    Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @GetMapping(value = "/error")
    public String handle(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        logger.debug(String.valueOf(status));
        return "notFound";
    }
}
