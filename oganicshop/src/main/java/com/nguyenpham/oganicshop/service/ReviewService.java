package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.MyReviewDto;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {

    List<ResponseReviewDto> getReviewsOfProduct(long productId);
    ResponseReviewDto save(RequestReviewDto postReview);
    List<ResponseReviewDto> getListReviews(User user);
    Page<Review> getMyReviews(User user, int pageNum, int pageSize);
    int likeComment(long reviewId, String action);
}
