package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Review;

public interface ReviewService {

    ResponseReviewDto save(RequestReviewDto postReview);
    Review update(RequestReviewDto review);
}
