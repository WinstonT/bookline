package com.example.demo.services;

import com.example.demo.models.Book;
import com.example.demo.models.Cart;
import com.example.demo.models.Review;
import com.example.demo.models.User;
import com.example.demo.repositories.BookRepository;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("localhost", 9200, "http")
            )
    );

    @Autowired
    private BookRepository bookRepository;

    public Book findBookById(String id){
        return bookRepository.findById(id).get();
    }

    public List<Book> findBook(String bookTitle){
        return bookRepository.findByBookTitle(bookTitle);
    }

    public List<Book> findBookByAuthor(String author){
        return bookRepository.findBookByBookAuthor(author);
    }

    public List<Book> findBookByCategory(String category){
        return bookRepository.findBookByBookCategory(category);
    }

    public List<Book> findBookContaining(String query){
        return bookRepository.findBooksByBookTitleIsContaining(query);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAllBy();
    }

    public void createBookIndexBulk(final List<Book> books){
        bookRepository.saveAll(books);
    }

    public void deleteBook(String bookIsbn){
        bookRepository.deleteBookByBookIsbn(bookIsbn);
    }

    public List<Book> updateBook(Book book, String field, String value){
        UpdateRequest updateRequest = new UpdateRequest("book", book.getId()).doc(field, value);
        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        RefreshRequest request = new RefreshRequest("book");
        try {
            client.indices().refresh(request, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public List<Cart> getCartList(User session){
        try{
            List<Cart> cartList = new ArrayList<>();
            for(String s: session.getCartList()){
                String[] array = s.split("_", 2);
                Cart cart = Cart.builder()
                        .book(findBook(array[0]).get(0))
                        .quantity(Integer.parseInt(array[1]))
                        .build();
                cartList.add(cart);
            }
            return cartList;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public long getTotalPrice(List<Cart> cartList){
        long total = 0;
        for (Cart cart: cartList){
            total = total + cart.getBook().getBookPrice() * cart.getQuantity();
        }
        return total;
    }

    public void deleteAll(){
        bookRepository.deleteAll();
    }
}
