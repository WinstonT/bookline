package com.example.demo.services;

import com.example.demo.models.Book;
import org.elasticsearch.common.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaginationService {

    @Autowired
    private BookService bookService;

    private static final double PAGE_SIZE = 20;

    public Map<Integer, List<Book>> getPage(@Nullable String query,
                                            @Nullable String sortBy,
                                            @Nullable String order,
                                            @Nullable int page){
        List<Book> bookList = new ArrayList<>();
        Map<Integer, List<Book>>  map = new HashMap<>();
        if(query != null && !query.isEmpty()) {
            bookList.addAll(bookService.findBook(query));
            bookList.addAll(bookService.findBookByAuthor(query));
            bookList.addAll(bookService.findBookByCategory(query));
        }
        else {
            bookList = bookService.getAllBooks();
        }
        if (sortBy == null){
            sortBy = "rating";
        }
        if (order == null){
            order = "desc";
        }
        if(sortBy.equals("rating")){
            if(order.equals("asc")){
                map.put(2, getPageWithRatingAsc(bookList, page));
            }
            else map.put(1, getPageWithRatingDesc(bookList, page));
        }
        if(sortBy.equals("price")){
            if(order.equals("asc")){
                map.put(4, getPageWithPriceAsc(bookList, page));
            }
            else map.put(3, getPageWithPriceDesc(bookList, page));
        }
        if(sortBy.equals("title")){
            if(order.equals("asc")){
                map.put(5, getPageWithTitleAsc(bookList, page));
            }
            else map.put(6, getPageWithTitleDesc(bookList, page));
        }
        return map;
    }

    public List<Book> checkEndPage(List<Book> bookList, int page){
        if(Math.floor(bookList.size() / PAGE_SIZE) * PAGE_SIZE != bookList.size() && Math.floor(bookList.size() / PAGE_SIZE) + 1 == page){
            return bookList.subList((int) ((page - 1) * PAGE_SIZE), bookList.size() - 1);
        }
        return bookList.subList((int) ((page - 1) * PAGE_SIZE), (int) (page * PAGE_SIZE));
    }

    public List<Book> getPageWithRatingDesc(List<Book> bookList, int page){
        return checkEndPage(bookList.stream().sorted(Comparator.comparing(Book::getBookRating).reversed()).collect(Collectors.toList()), page);
    }

    public List<Book> getPageWithRatingAsc(List<Book> bookList, int page){
        return checkEndPage(bookList.stream().sorted(Comparator.comparing(Book::getBookRating)).collect(Collectors.toList()), page);
    }

    public List<Book> getPageWithPriceDesc(List<Book> bookList, int page){
        return checkEndPage(bookList.stream().sorted(Comparator.comparing(Book::getBookPrice).reversed()).collect(Collectors.toList()), page);
    }

    public List<Book> getPageWithPriceAsc(List<Book> bookList, int page){
        return checkEndPage(bookList.stream().sorted(Comparator.comparing(Book::getBookPrice)).collect(Collectors.toList()), page);
    }

    public List<Book> getPageWithTitleDesc(List<Book> bookList, int page){
        return checkEndPage(bookList.stream().sorted(Comparator.comparing(Book::getBookTitle).reversed()).collect(Collectors.toList()), page);
    }

    public List<Book> getPageWithTitleAsc(List<Book> bookList, int page){
        return checkEndPage(bookList.stream().sorted(Comparator.comparing(Book::getBookTitle)).collect(Collectors.toList()), page);
    }

    public int getLastPageIndex(List<Book> bookList) {
        if (Math.floor(bookList.size() / PAGE_SIZE) * PAGE_SIZE != bookList.size()) {
            return (int) (Math.floor(bookList.size() / PAGE_SIZE) + 1);
        }
        return (int) Math.floor(bookList.size() / PAGE_SIZE);
    }
}

// TODO : pagination with query last page