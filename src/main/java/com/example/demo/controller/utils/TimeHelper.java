package com.example.demo.controller.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeHelper {

    public int getCurrentYear(){
        LocalDate date = LocalDate.now();
        return date.getYear();
    }

    public LocalDate getDateNow(){
        return LocalDate.now();
    }

    public LocalDateTime getDateTimeNow(){
        return LocalDateTime.now();
    }
}
