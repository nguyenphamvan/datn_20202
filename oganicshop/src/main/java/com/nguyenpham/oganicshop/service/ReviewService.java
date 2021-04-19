package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.MyReviewDto;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.User;

import java.util.List;

public interface ReviewService {

    List<ResponseReviewDto> getReviewsOfProduct(long productId);
    ResponseReviewDto save(RequestReviewDto postReview);
    List<ResponseReviewDto> getListReviews(User user);
    int likeComment(long reviewId, String action);
}
