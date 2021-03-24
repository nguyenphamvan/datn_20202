package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/review")
public class ReviewControllerApi {

    private ReviewService reviewService;
    private OrderService orderService;

    @Autowired
    public ReviewControllerApi(ReviewService reviewService, OrderService orderService) {
        this.reviewService = reviewService;
        this.orderService = orderService;
    }

    @GetMapping("/my-review")
    public ResponseEntity<?> getListReview() {
        return ResponseEntity.ok(reviewService.getListReviews());
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
            String uploadDir = "C:/Users/nguye/Desktop/datn_20202/oganicshop/src/main/resources/static/images/image-product-review/" + requestReviewDto.getProductId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            return ResponseEntity.ok("file " + file.getOriginalFilename() + " đã được upload vào thư mục : " + uploadDir);
        } catch (IOException e) {
            return new ResponseEntity<>("Có lỗi trong quá trình xử lý!", HttpStatus.BAD_REQUEST);
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

}
