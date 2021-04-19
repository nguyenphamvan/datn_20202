package com.nguyenpham.oganicshop.controller.user;

import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

    @PostMapping("/add-product-review")
    public Review addProductReview() {
        return null;
    }

    @PostMapping("/reply-review")
    public Review replyComment() {
        return null;
    }
}
