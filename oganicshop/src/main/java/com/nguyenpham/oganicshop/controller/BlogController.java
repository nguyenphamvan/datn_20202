package com.nguyenpham.oganicshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/blogs")
    public String viewBlogPage() {
        return "blogs";
    }
}
