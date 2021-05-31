package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ReviewRequest;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.dto.SubReviewRequest;
import com.nguyenpham.oganicshop.dto.SubReviewResponse;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    List<ReviewResponse> getReviewsOfProduct(String productUrl);
    ReviewResponse saveReview(ReviewRequest postReview);
    SubReviewResponse saveSubReview(SubReviewRequest postSubReview);
    List<ReviewResponse> getListReviews(User user);
    Page<Review> getMyReviews(long userId, int pageNum, int pageSize);
    int likeComment(long reviewId, String action);
}
