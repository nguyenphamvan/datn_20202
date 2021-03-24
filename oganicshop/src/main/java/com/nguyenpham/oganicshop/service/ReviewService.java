package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.MyReviewDto;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Review;

import java.util.List;

public interface ReviewService {

    ResponseReviewDto save(RequestReviewDto postReview);
    List<MyReviewDto> getListReviews();
    Review update(RequestReviewDto review);
}
