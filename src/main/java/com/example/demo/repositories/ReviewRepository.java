package com.example.demo.repositories;

import com.example.demo.models.Review;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ReviewRepository extends ElasticsearchRepository<Review, String> {

    List<Review> findByBookId(String bookId);

    List<Review> findByUserName(String userId);
}
