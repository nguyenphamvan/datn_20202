package com.nguyenpham.oganicshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user/home");
        registry.addViewController("/403").setViewName("error/403");
        registry.addViewController("/loginAdmin").setViewName("admin/loginAdmin");
        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/signup").setViewName("user/signup");
        registry.addViewController("/contact").setViewName("user/contact");
        registry.addViewController("/payment_success").setViewName("user/payment-success");

        registry.addViewController("/customer/account/edit").setViewName("user/account-info");
        registry.addViewController("/customer/account/info").setViewName("user/account-info");
        registry.addViewController("/customer/wishlist").setViewName("user/account-wishlist");
        registry.addViewController("/customer/address").setViewName("user/account-shipping-address");
        registry.addViewController("/nhan-xet-san-pham-ban-da-mua").setViewName("user/account-purchased-product");
        registry.addViewController("/customer/review").setViewName("user/account-reviewed");

        registry.addViewController("/cart.html").setViewName("user/cart");
        registry.addViewController("/contact").setViewName("user/contact");
        registry.addViewController("/collections.html").setViewName("user/collection");

        registry.addViewController("/search").setViewName("user/search");
        registry.addViewController("/promotion.html").setViewName("user/promotion");
    }
}
