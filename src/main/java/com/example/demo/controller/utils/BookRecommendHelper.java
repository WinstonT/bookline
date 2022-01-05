package com.example.demo.controller.utils;

import com.example.demo.models.Book;
import com.example.demo.models.Cart;
import com.example.demo.models.Order;
import com.example.demo.services.BookService;
import com.example.demo.services.OrderService;
import com.example.demo.services.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BookRecommendHelper {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaginationService paginationService;

    public List<Book> getRecommendedBooks(){
        List<Book> recommendedBooks = new ArrayList<>();
        if(Session.getSession() != null && Session.getSession().getUserRole().equals("user")){
            List<Book> orderedBooks = orderService.getPurchasedBooks(Session.getSession().getId());
            Map<String, Long> countCategory = orderedBooks.stream().collect(Collectors.groupingBy(Book::getBookCategory, Collectors.counting()));
            Map<String, Long> countAuthor = orderedBooks.stream().collect(Collectors.groupingBy(Book::getBookAuthor, Collectors.counting()));
            for(Map.Entry entry: countCategory.entrySet()){
                List<Book> bookList = bookService.findBookByCategory(entry.getKey().toString());
                long value = (long) entry.getValue();
                List<Integer> list = new ArrayList<>();
                for(int i = 0; i < value; i++){
                    recommendedBooks.add(bookList.get((int) (Math.random() * (bookList.size() - 1))));
                }
            }
            for(Map.Entry entry: countAuthor.entrySet()){
                recommendedBooks.addAll(bookService.findBookByAuthor(entry.getKey().toString()));
                for(Book ordered : orderedBooks){
                    recommendedBooks.removeIf(book1 -> book1.equals(ordered));
                }
            }
        }
        else{
            recommendedBooks.addAll(paginationService.getPage(null, "rating", "desc", 1).entrySet().iterator().next().getValue());
            recommendedBooks.addAll(paginationService.getPage(null, "rating", "desc", 2).entrySet().iterator().next().getValue());
            recommendedBooks.addAll(paginationService.getPage(null, "rating", "desc", 3).entrySet().iterator().next().getValue());
            recommendedBooks.addAll(paginationService.getPage(null, "rating", "desc", 4).entrySet().iterator().next().getValue());
        }
        Collections.shuffle(recommendedBooks);
        return recommendedBooks.subList(0, 8);
    }

    public List<Book> getBestSellerBook(){
        List<Book> bookList = new ArrayList<>();
        for(Order order: orderService.getAllOrders()){
            for(Cart cart: order.getCartItems()){
                bookList.add(cart.getBook());
            }
        }
        Map<String, Long> orderedBooksCount = bookList.stream().collect(Collectors.groupingBy(Book::getId, Collectors.counting()));
        List<Book> bestSellers = new ArrayList<>();
        for(Map.Entry entry: orderedBooksCount.entrySet()){
            bestSellers.add(bookService.findBookById(entry.getKey().toString()));
        }
        if(bestSellers.size() < 8){
            bestSellers.addAll(paginationService.getPage(null, "rating", "desc", 1).entrySet().iterator().next().getValue().subList(0, 8 - bestSellers.size()));
        }
        return bestSellers;
    }

    public List<Book> getRecentlyAddedBooks(){
        return bookService.getAllBooks().subList(bookService.getAllBooks().size() - 9, bookService.getAllBooks().size() - 1);
    }

    public List<Book> getPopularCategoryBook(String category){
        return bookService.findBookByCategory(category).subList(0, 4);
    }
}
