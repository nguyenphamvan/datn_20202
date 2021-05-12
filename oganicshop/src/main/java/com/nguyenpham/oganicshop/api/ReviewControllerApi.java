package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.ReviewConverter;
import com.nguyenpham.oganicshop.dto.MyReviewDto;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Rating;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.*;
import com.nguyenpham.oganicshop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/review")
public class ReviewControllerApi {

    private ReviewService reviewService;
    private OrderService orderService;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public ReviewControllerApi(ReviewService reviewService, OrderService orderService, ProductService productService, UserService userService) {
        this.reviewService = reviewService;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/my-review")
    public ResponseEntity<?> getMyReviews(@RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Map<String, Object> response = new HashMap<>();
        Page<Review> pageReview = reviewService.getMyReviews(user, currentPage, pageSize);
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
    public ResponseEntity<?> postReview(@RequestBody RequestReviewDto requestReviewDto) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setId(requestReviewDto.getOrderDetailId());
        orderDetailDto.setReviewed(true);
        try {
            userService.rateProduct(requestReviewDto, user.getId());
            orderService.editReviewed(orderDetailDto);
            reviewService.save(requestReviewDto);
            return ResponseEntity.ok("Cảm ơn bạn đã đánh giá sản phẩm!");
        } catch (Exception e) {
            return new ResponseEntity<>("Có lỗi trong quá trình xử lý!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reply")
    public ResponseEntity<?> replyReview(@RequestBody RequestReviewDto replyReview) {
        ResponseReviewDto response = reviewService.save(replyReview);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return new ResponseEntity<>("Có lỗi trong quá trình xử lý!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/likeComment")
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
