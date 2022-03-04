package com.example.demo.services;

import com.example.demo.models.Book;
import org.elasticsearch.common.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaginationService {

    @Autowired
    private BookService bookService;

    private static final double PAGE_SIZE = 20;

    public Map<String, List<Book>> getPage(@Nullable String query,
                                            @Nullable String sortBy,
                                            @Nullable String order,
                                            @Nullable int page){
        List<Book> bookList = new ArrayList<>();
        Map<String, List<Book>>  map = new HashMap<>();
        int totalResults = 0;
        if(query != null && !query.isEmpty()){
            try{
                bookList.addAll(bookService.findBookContaining(query));
            }
            catch (InvalidDataAccessApiUsageException e){
                bookList.addAll(bookService.findBook(query));
            }
            finally {
                bookList.addAll(bookService.findBookByAuthor(query));
                bookList.addAll(bookService.findBookByCategory(query));
                bookList = bookList.stream().distinct().collect(Collectors.toList());
                totalResults = bookList.size();
            }
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
        if(totalResults > 1){
            totalResults = totalResults - 1;
        }
        String lastPage = getLastPageIndex(bookList);
        String key = "" + lastPage + "_" + totalResults;
        if(sortBy.equals("rating")){
            if(order.equals("asc")){
                map.put("2_" + key, getPageWithRatingAsc(bookList, page));
            }
            else map.put("1_" + key, getPageWithRatingDesc(bookList, page));
        }
        if(sortBy.equals("price")){
            if(order.equals("asc")){
                map.put("4_" + key, getPageWithPriceAsc(bookList, page));
            }
            else map.put("3_" + key, getPageWithPriceDesc(bookList, page));
        }
        if(sortBy.equals("title")){
            if(order.equals("asc")){
                map.put("5_" + key, getPageWithTitleAsc(bookList, page));
            }
            else map.put("6_" + key, getPageWithTitleDesc(bookList, page));
        }
        return map;
    }

    public List<Book> checkEndPage(List<Book> bookList, int page){
        if(bookList.size() == 1 || bookList.size() == 0){
            return bookList;
        }
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

    public String getLastPageIndex(List<Book> bookList) {
        if (Math.floor(bookList.size() / PAGE_SIZE) * PAGE_SIZE != bookList.size()) {
            return (int) (Math.floor(bookList.size() / PAGE_SIZE) + 1) + "";
        }
        return (int) Math.floor(bookList.size() / PAGE_SIZE) + "";
    }
}