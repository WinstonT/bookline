package com.example.demo.controller.utils;

import com.example.demo.models.Book;
import com.example.demo.models.Message;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {

    TimeHelper timeHelper = new TimeHelper();

    private boolean checkString(String string){
        try{
            int val = Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public Message validateBook(Book book){

        String text = "";
        int pos = 0;

        if(book.getBookTitle().isEmpty()){
            text = "Book title cannot be empty";
            pos = 1;
        }
        else if(book.getBookAuthor().isEmpty()){
            text = "Book author cannot be empty";
            pos = 2;
        }
        else if(book.getBookCategory().isEmpty()){
            text = "Book category cannot be empty";
            pos = 3;
        }
        else if(book.getBookDescription().isEmpty()){
            text = "Book description cannot be empty";
            pos = 4;
        }
        else if(!book.getBookImage().contains("http://") && !book.getBookImage().isEmpty()){
            text = "Book image must be image URL";
            pos = 5;
        }
        else if(checkString(book.getBookIsbn())){
            text = "Book ISBN must consist of numbers only";
            pos = 6;
        }
        else if(book.getBookIsbn().length() != 13){
            text = "Book ISBN must contain 13 digits";
            pos = 6;
        }
        else if(!checkString(book.getYearPublished())){
            text = "Book year must be a number";
            pos = 7;
        }
        else if(book.getYearPublished().isEmpty()){
            text = "Year published cannot be empty";
            pos = 7;
        }
        else if(Integer.parseInt(book.getYearPublished()) < 1900 || Integer.parseInt(book.getYearPublished()) > timeHelper.getCurrentYear()){
            text = "Book year must be between 1900 and " + timeHelper.getCurrentYear();
            pos = 7;
        }
        else if(book.getBookPages().isEmpty()){
            text = "Book pages cannot be empty";
            pos = 8;
        }
        else if(!checkString(book.getBookPages())){
            text = "Book pages must be a number";
            pos = 8;
        }
        else if(Integer.parseInt(book.getBookPages()) < 0){
            text = "Book pages must be greater than 0";
            pos = 8;
        }
        else if(book.getBookPrice() <= 0){
            text = "Book price must be greater than 0";
            pos = 9;
        }
        else if(book.getBookRating() > 5 || book.getBookRating() <= 0){
            text = "Book rating must between 0 and 5";
            pos = 10;
        }
        else text = "true";

        return new Message(text, pos);
    }
}
