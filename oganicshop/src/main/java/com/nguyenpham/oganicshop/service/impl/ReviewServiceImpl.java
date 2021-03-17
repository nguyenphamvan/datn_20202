package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.repository.ReviewRepository;
import com.nguyenpham.oganicshop.repository.UserRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ResponseReviewDto save(RequestReviewDto postReview) {
        Review review = new Review();
        review.setTitle(postReview.getTitle());
        review.setComment(postReview.getComment());
        review.setRatting(postReview.getRatting());
        if (postReview.getRootId() != null) {
            Review rootReview = reviewRepository.findById(postReview.getRootId()).orElse(null);
            review.setRootComment(rootReview);
            review.setProduct(rootReview.getProduct());
        } else {
            Product product = productRepository.findById(postReview.getProductId()).orElse(null);
            if (product != null) {
                review.setProduct(product);
            }
        }
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (user != null) {
            review.setUser(user);
        }
        return reviewRepository.save(review).convertReviewToReviewDto();
    }

    @Override
    public Review update(RequestReviewDto review) {
        return null;
    }
}
