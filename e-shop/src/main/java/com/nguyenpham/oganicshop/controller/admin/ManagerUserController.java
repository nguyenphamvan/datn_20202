package com.nguyenpham.oganicshop.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/manager_users")
public class ManagerUserController {

    @GetMapping
    public String viewManagerUsers() {
        return "admin/manager_users";
    }

    @GetMapping("/user/{userId}")
    public String viewManagerUserDetail(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/manager-userDetail";
    }

    @GetMapping("/user/{userId}/orderHistory")
    public String viewMaxOrderHistory(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/manager-orderHistory";
    }

    @GetMapping("/user/{userId}/maxOrderHistory")
    public String viewOrderHistory(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/manager-maxOrderHistory";
    }

    @GetMapping("/user/{userId}/reviews")
    public String viewReviewsHistory(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/manager-reviewsHistory";
    }

    @GetMapping("/user/{userId}/wishlist")
    public String viewWishList(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("userId", userId);
        return "admin/manager-wishlist";
    }
}
