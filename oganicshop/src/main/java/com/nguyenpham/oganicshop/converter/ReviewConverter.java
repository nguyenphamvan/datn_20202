package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.MyReviewDto;
import com.nguyenpham.oganicshop.dto.ReviewRequest;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.entity.Review;

public class ReviewConverter implements GeneralConverter<Review, ReviewRequest, ReviewResponse>{
    @Override
    public ReviewResponse entityToDto(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setComment(review.getComment());
        response.setTitle(review.getTitle());
        response.setRating(review.getRating());
        response.setReviewerName(review.getUser().getFullName());
        response.setUserId(review.getUser().getId());
        response.setProductId(review.getProduct().getId());
        response.setCreatedAt(review.getCreatedAt());
        response.setNumbersOfLike(review.getNumbersOfLike());
        if (review.getSubReviews() != null) {
            for (Review subReview : review.getSubReviews()) {
                ReviewResponse subReviewDto = new ReviewResponse();
                subReviewDto.setId(subReview.getId());
                subReviewDto.setUserId(subReview.getUser().getId());
                subReviewDto.setReviewerName(subReview.getUser().getFullName());
                subReviewDto.setIdRootReview(subReview.getRootComment().getId());
                subReviewDto.setComment(subReview.getComment());
                subReviewDto.setTitle(subReview.getTitle());
                subReviewDto.setCreatedAt(subReview.getCreatedAt());
                subReviewDto.setNumbersOfLike(subReview.getNumbersOfLike());
                response.addSubReview(subReviewDto);
            }
        }
        return response;
    }

    public MyReviewDto entityToMyReview(Review review) {
        MyReviewDto response = new MyReviewDto();
        response.setId(review.getId());
        response.setComment(review.getComment());
        response.setTitle(review.getTitle());
        response.setRating(review.getRating());
        response.setProductImg("/images/products/" + review.getProduct().getId() + "/" + review.getProduct().getImage().split(",")[0]);
        response.setReviewerName(review.getUser().getFullName());
        response.setUserId(review.getUser().getId());
        response.setProductUrl(review.getProduct().getUrl());
        response.setProductName(review.getProduct().getName());
        response.setCreatedAt(review.getCreatedAt());
        response.setUpdatedAt(review.getUpdatedAt());

        return response;
    }

    @Override
    public Review dtoToEntity(ReviewRequest d) {
        return null;
    }
}
