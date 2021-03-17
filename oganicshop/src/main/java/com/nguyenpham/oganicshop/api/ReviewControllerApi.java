package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.RequestReviewDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/review")
public class ReviewControllerApi {

    private ReviewService reviewService;

    @Autowired
    public ReviewControllerApi(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<Object> postReview(@RequestBody RequestReviewDto newReview) {
        return ResponseEntity.ok(reviewService.save(newReview));
    }

    @PostMapping("/reply")
    @ResponseBody
    public ResponseEntity<Object> replyReview(@RequestBody RequestReviewDto replyReview) {
        return ResponseEntity.ok(reviewService.save(replyReview));
    }
}
