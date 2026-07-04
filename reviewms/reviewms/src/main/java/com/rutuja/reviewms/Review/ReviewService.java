package com.rutuja.reviewms.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long companyId);
    Boolean addReviews(Long companyId,Review review);
    Review getReview(Long reviewId);
    Boolean updateReview(Long reviewId,Review review);

    Boolean deleteReview(Long reviewId);
}
