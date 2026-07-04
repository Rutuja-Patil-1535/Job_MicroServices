package com.rutuja.reviewms.Review.impl;


import com.rutuja.reviewms.Review.Review;
import com.rutuja.reviewms.Review.ReviewRepository;
import com.rutuja.reviewms.Review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository repository;
    public ReviewServiceImpl(ReviewRepository repository) {
        this.repository = repository;

    }
    @Override
    public List<Review> getAllReviews(Long companyId) {
       List<Review> reviews=repository.findByCompanyId(companyId);
       return reviews;
    }
    @Override
    public Boolean addReviews(Long companyId, Review review) {

        if(review!=null && companyId!=null){
            review.setCompanyId(companyId);
            repository.save(review);
            return true;
        }
        return false;
    }
    @Override
    public Review getReview(Long reviewId) {
        if(reviewId!=null) {
            Review review = repository.findById(reviewId).orElse(null);
            return review;
        }
        return null;
    }
    @Override
    public Boolean updateReview(Long reviewId,Review review) {
        Review ureview=repository.findById(reviewId).orElse(null);
        if(ureview!=null){
            ureview.setTitle(review.getTitle());
            ureview.setDescription(review.getDescription());
            ureview.setTitle(review.getTitle());
            ureview.setRating(review.getRating());
            ureview.setCompanyId(review.getCompanyId());
            repository.save(ureview);
            return true;

        }
        return false;
    }

    @Override
    public Boolean deleteReview(Long reviewId) {
        Review dreview =repository.findById(reviewId).orElse(null);

            if(dreview!=null){
                repository.deleteById(reviewId);
                return true;
            }

        return false;
    }
}
