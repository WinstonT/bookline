package com.example.demo.repositories;

import com.example.demo.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookRepository extends ElasticsearchRepository<Book, String> {

    List<Book> findByBookTitle(String bookTitle);

    List<Book> findBookByBookAuthor(String bookAuthor);

    List<Book> findBookByBookCategory(String bookCategory);

    List<Book> findBooksByBookTitleIsContaining(String query);

    void deleteBookByBookIsbn(String bookIsbn);

    List<Book> findAllBy();

}
