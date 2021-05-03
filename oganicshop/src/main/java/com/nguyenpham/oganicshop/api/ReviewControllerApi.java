package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewControllerApi {

    private ReviewService reviewService;
    private OrderService orderService;
    private CategoryService categoryService;

    @Autowired
    public ReviewControllerApi(ReviewService reviewService, OrderService orderService, CategoryService categoryService) {
        this.reviewService = reviewService;
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping("/my-review")
    public ResponseEntity<?> getListReview() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewService.getMyReviews(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/post")
    public ResponseEntity<?> postReview(@ModelAttribute RequestReviewDto requestReviewDto) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setId(requestReviewDto.getOrderDetailId());
        orderDetailDto.setReviewed(true);
        try {
            orderService.editReviewed(orderDetailDto);
            reviewService.save(requestReviewDto);
            MultipartFile file = requestReviewDto.getImage();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "static/images/image-product-review/" + requestReviewDto.getProductId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            return ResponseEntity.ok("file " + file.getOriginalFilename() + " đã được upload thành công");
        } catch (IOException e) {
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
