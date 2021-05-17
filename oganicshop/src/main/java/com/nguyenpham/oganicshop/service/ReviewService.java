package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ReviewRequest;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    List<ReviewResponse> getReviewsOfProduct(long productId);
    ReviewResponse save(ReviewRequest postReview);
    List<ReviewResponse> getListReviews(User user);
    Page<Review> getMyReviews(long userId, int pageNum, int pageSize);
    int likeComment(long reviewId, String action);
}
