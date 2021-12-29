package com.example.demo.controller.mvc;

import com.example.demo.controller.utils.BookValidator;
import com.example.demo.models.Book;
import com.example.demo.models.Message;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditBookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookValidator bookValidator;

    private Book oldBook;

    @GetMapping(value = "/admin/books/edit/{title}")
    public String editBook(@ModelAttribute("title") String bookTitle, Model model){
        oldBook = bookService.findBook(bookTitle).get(0);
        model.addAttribute("book", oldBook);
        return "editBook";
    }

    @PostMapping(value = "/admin/books/edit")
    public String addNewBook(@ModelAttribute("book") Book book, Model model){
        Message message = bookValidator.validateBook(book);
        if(message.getPosition() != 0){
            model.addAttribute("error", message.getText());
            model.addAttribute("position", message.getPosition());
            return "editBook";
        }
        else{
            if(!book.getBookAuthor().equals(oldBook.getBookAuthor())){
                bookService.updateBook(oldBook, "bookAuthor", book.getBookAuthor());
            }
            if(!book.getBookDescription().equals(oldBook.getBookDescription())){
                bookService.updateBook(oldBook, "bookDescription", book.getBookDescription());
            }
            if(!book.getBookCategory().equals(oldBook.getBookCategory())){
                bookService.updateBook(oldBook, "bookCategory", book.getBookCategory());
            }
            if(!book.getBookPages().equals(oldBook.getBookPages())){
                bookService.updateBook(oldBook, "bookPages", book.getBookPages());
            }
            if(book.getBookRating() != oldBook.getBookRating()){
                bookService.updateBook(oldBook, "bookRating", "" + book.getBookRating());
            }
            if(book.getBookPrice() != oldBook.getBookPrice()){
                bookService.updateBook(oldBook, "bookPrice", "" + book.getBookPrice());
            }
            if(!book.getYearPublished().equals(oldBook.getYearPublished())){
                bookService.updateBook(oldBook, "yearPublished", book.getYearPublished());
            }

            return "redirect:/admin/books";
        }

    }
}
