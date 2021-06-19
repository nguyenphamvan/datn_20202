package com.nguyenpham.oganicshop.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {
    @GetMapping("/products/{productUrl}")
    public String getProductDetail(@PathVariable("productUrl") String productUrl) {
        return "user/product";
    }
}
