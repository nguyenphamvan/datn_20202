package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.MyReviewDto;
import com.nguyenpham.oganicshop.dto.ReviewRequest;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.dto.SubReviewResponse;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.SubReview;
import com.nguyenpham.oganicshop.util.DateTimeUtil;

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
            for (SubReview subReview : review.getSubReviews()) {
                SubReviewResponse subReviewDto = new SubReviewResponse();
                subReviewDto.setId(subReview.getId());
                subReviewDto.setReviewerName(subReview.getUser().getFullName());
                subReviewDto.setRootReviewId(review.getId());
                subReviewDto.setContent(subReview.getContent());
                subReviewDto.setCreatedDate(DateTimeUtil.dateFormat(subReview.getCreatedAt()));
                response.addSubReview(subReviewDto);
            }
        }
        return response;
    }

    public MyReviewDto entityToMyReview(Review review) {
        MyReviewDto response = new MyReviewDto();
        response.setId(review.getId());
        response.setProductUrl(review.getProduct().getProductUrl());
        response.setComment(review.getComment());
        response.setTitle(review.getTitle());
        response.setRating(review.getRating());
        response.setProductImg(review.getProduct().getMainImage());
        response.setReviewerName(review.getUser().getFullName());
        response.setUserId(review.getUser().getId());

        response.setProductUrl(review.getProduct().getProductUrl());
        response.setProductName(review.getProduct().getTitle());
        response.setCreatedAt(DateTimeUtil.dateTimeFormat(review.getCreatedAt()));
        response.setUpdatedAt(DateTimeUtil.dateTimeFormat(review.getUpdatedAt()));

        return response;
    }

    @Override
    public Review dtoToEntity(ReviewRequest d) {
        return null;
    }
}
