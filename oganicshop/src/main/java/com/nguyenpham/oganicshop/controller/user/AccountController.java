package com.nguyenpham.oganicshop.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/customer/account/edit")
    public String myAccountPage() {
        return "account-info";
    }

    @GetMapping("/customer/wishlist")
    public String getMyWishlish() {
        return "user/account-wishlist";
    }

    @GetMapping("/customer/address")
    public String getMyShippingAddress() {
        return "user/account-shipping-address";
    }

    @GetMapping("/nhan-xet-san-pham-ban-da-mua")
    public String getPurchasedProduct() {
        return "user/account-purchased-product";
    }

    @GetMapping("/customer/review")
    public String getMyReview() {
        return "user/account-reviewed";
    }
}
