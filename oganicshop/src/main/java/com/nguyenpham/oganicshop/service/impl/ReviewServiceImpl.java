package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.converter.ReviewConverter;
import com.nguyenpham.oganicshop.dto.ReviewRequest;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.dto.SubReviewRequest;
import com.nguyenpham.oganicshop.dto.SubReviewResponse;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.SubReview;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.repository.ReviewRepository;
import com.nguyenpham.oganicshop.repository.SubReviewRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private SubReviewRepository subReviewRepository;
    private ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, SubReviewRepository subReviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.subReviewRepository = subReviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ReviewResponse> getReviewsOfProduct(String productUrl) {
        List<Review> listReviews = reviewRepository.findAllByProductUrl(productUrl);
        ReviewConverter converter = new ReviewConverter();
        List<ReviewResponse> listReviewDto = listReviews.stream().map(rv -> converter.entityToDto(rv)).collect(Collectors.toList());
        return listReviewDto;
    }

    @Override
    public List<Review> getReviewsOfProduct(long productId) {
        return reviewRepository.findAllByProductId(productId);
    }

    @Override
    public ReviewResponse saveReview(ReviewRequest postReview) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Review review = new Review();
        review.setTitle(postReview.getTitle());
        review.setComment(postReview.getComment());
        review.setRating(postReview.getRating());
        if (postReview.getRootId() != null) {
            Review rootReview = reviewRepository.findById(postReview.getRootId()).orElse(null);
            review.setProduct(rootReview.getProduct());
        } else {
            Product product = productRepository.findById(postReview.getProductId()).orElse(null);
            if (product != null) {
                review.setProduct(product);
            }
        }
        review.setUser(user);
        review = reviewRepository.save(review);
        return new ReviewConverter().entityToDto(review);
    }

    @Override
    public SubReviewResponse saveSubReview(SubReviewRequest postSubReview) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Review parentReview = reviewRepository.findById(postSubReview.getParentId()).orElse(null);
        if (parentReview != null) {
            SubReview subReview = new SubReview();
            subReview.setReview(parentReview);
            subReview.setUser(parentReview.getUser());
            subReview.setContent(postSubReview.getContent());
            subReview.setReview(parentReview);
            try {
                subReviewRepository.save(subReview);
                SubReviewResponse subReviewResponse = new SubReviewResponse();
                subReviewResponse.setReviewerId(user.getId());
                subReviewResponse.setReviewerName(user.getFullName());
                subReviewResponse.setCreatedDate(DateTimeUtil.dateFormat(new Date()));
                subReviewResponse.setContent(postSubReview.getContent());
                subReviewResponse.setRootReviewId(postSubReview.getParentId());
                return subReviewResponse;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public List<ReviewResponse> getListReviews(User user) {
        List<Review> listReviews = reviewRepository.findReviewsByUserId(user.getId());
        ReviewConverter converter = new ReviewConverter();
        List<ReviewResponse> listReviewDto = listReviews.stream().map(rv -> converter.entityToDto(rv)).collect(Collectors.toList());
        return listReviewDto;
    }

    @Override
    public Page<Review> getMyReviews(long userId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id"));
        Page<Review> listReviews = reviewRepository.findAllByUserId(userId, pageable);
        return listReviews;
    }

    @Override
    public int likeComment(long reviewId, String action) {
        Review review = reviewRepository.findById(reviewId).get();
        if (action.equals("like")) {
            review.setNumbersOfLike(review.getNumbersOfLike() + 1);
        } else if (action.equals("cancel")) {
            review.setNumbersOfLike(review.getNumbersOfLike() - 1);
        }
        return reviewRepository.save(review).getNumbersOfLike();
    }
}
