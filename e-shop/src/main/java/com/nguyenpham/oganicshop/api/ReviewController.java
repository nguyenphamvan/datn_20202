package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.ReviewConverter;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.*;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewService reviewService;
    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public ReviewController(ReviewService reviewService, OrderService orderService, ProductService productService) {
        this.reviewService = reviewService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/review_of_product")
    public ResponseEntity<?> getReviewsOfProduct(@RequestParam("productUrl") String productUrl) {
        BaseResponse br = new BaseResponse();
        List<ReviewResponse> listReview = reviewService.getReviewsOfProduct(productUrl);
        if (listReview.size() > 0) {
            br.setData(listReview);
            br.setStatus(true);
        } else {
            br.setStatus(false);
        }
        return ResponseEntity.ok(br);
    }

    @GetMapping("/my-review")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getMyReviews(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Map<String, Object> response = new HashMap<>();
        Page<Review> pageReview = reviewService.getMyReviews(user.getId(), currentPage, pageSize);
        ReviewConverter converter = new ReviewConverter();
        List<MyReviewDto> listMyReview = pageReview.getContent().stream().map(rv -> converter.entityToMyReview(rv))
                .collect(Collectors.toList());
        response.put("reviews", listMyReview);
        response.put("totalPage", pageReview.getTotalPages());
        response.put("currentPage", pageReview.getNumber() + 1);
        response.put("pageSize", pageReview.getSize());
        response.put("numberOfReview", pageReview.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/post")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> postReview(@RequestBody ReviewRequest reviewRequest) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        try {
            orderService.updateProductReviewed(user.getId(), reviewRequest.getProductId());
            reviewService.saveReview(reviewRequest);
            // tính điểm đánh giá trung bình của sản phẩm ở đây
            List<Review> listReviewOfProduct = reviewService.getReviewsOfProduct(reviewRequest.getProductId());
            int totalRating = 0;
            for (Review r : listReviewOfProduct) {
                totalRating += r.getRating();
            }

            /// cập nhật các thông tin về số ratting , ratting trung bình
            double meanRating = totalRating / listReviewOfProduct.size();
            Product product = productService.getProductDetail(reviewRequest.getProductId());
            product.setAverageRating(meanRating);
            productService.save(product);

            return ResponseEntity.ok("Cảm ơn bạn đã đánh giá sản phẩm!");
        } catch (Exception e) {
            return new ResponseEntity<>("Có lỗi trong quá trình xử lý!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") long reviewId) {
        return ResponseEntity.ok(reviewService.deleteReview(reviewId));
    }

    @PostMapping("/reply")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> replyReview(@RequestBody SubReviewRequest postSubReview) {
        SubReviewResponse response = reviewService.saveSubReview(postSubReview);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>("Có lỗi trong quá trình xử lý!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/likeComment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public int likeComment(@RequestBody ObjectNode object) {
        try {
            long reviewId = object.get("reviewId").asLong();
            String action = object.get("action").asText();
            return reviewService.likeComment(reviewId, action);
        } catch (Exception e) {
            return 0;
        }
    }

}
