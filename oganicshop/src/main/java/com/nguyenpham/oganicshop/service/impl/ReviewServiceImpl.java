package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.converter.ReviewConverter;
import com.nguyenpham.oganicshop.dto.MyReviewDto;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ResponseReviewDto> getReviewsOfProduct(long productId) {
        List<Review> listReviews = reviewRepository.findAllByProductIdAndRootCommentIsNull(productId);
        ReviewConverter converter = new ReviewConverter();
        List<ResponseReviewDto> listReviewDto = listReviews.stream().map(rv ->converter.entityToDto(rv)).collect(Collectors.toList());
        return listReviewDto;
    }

    @Override
    public ResponseReviewDto save(RequestReviewDto postReview) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Review review = new Review();
        review.setTitle(postReview.getTitle());
        review.setComment(postReview.getComment());
        review.setRating(postReview.getRating());
        if (postReview.getImage() != null) {
            review.setImg(postReview.getImage().getOriginalFilename()); // lấy hình ảnh, sẽ lưu ý để làm cách khác tưởng minh hơn
        }

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
        review.setUser(user);
        review = reviewRepository.save(review);
        return new ReviewConverter().entityToDto(review);
    }

    @Override
    public List<ResponseReviewDto> getListReviews(User user) {
        List<Review> listReviews = reviewRepository.findAllByUserId(user.getId());
        ReviewConverter converter = new ReviewConverter();
        List<ResponseReviewDto> listReviewDto = listReviews.stream().map(rv -> converter.entityToDto(rv)).collect(Collectors.toList());
        return listReviewDto;
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
