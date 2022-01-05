package com.example.demo.services;

import com.example.demo.models.Review;
import com.example.demo.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void saveReview(Review review){
        reviewRepository.save(review);
    }

    public List<Review> getReviewByBook(String bookId){
        return reviewRepository.findByBookId(bookId);
    }

    public List<Review> getReviewByUser(String userId){
        return reviewRepository.findByUserName(userId);
    }

    public double getAverageScore(String bookId){
        double sum = 0;
        for(Review review: getReviewByBook(bookId)){
            sum = sum + review.getScore();
        }
        double avgScore = sum / getReviewByBook(bookId).size();
        double scale = Math.pow(10, 1);
        return Math.round(avgScore * scale) / scale;
    }
}
