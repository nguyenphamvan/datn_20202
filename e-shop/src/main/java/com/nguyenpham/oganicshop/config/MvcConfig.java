package com.nguyenpham.oganicshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*System.out.println("Configuration file has taken effect");*/
        registry.addResourceHandler("/images/**").addResourceLocations("file:C:\\Users\\nguyenpv\\Desktop\\datn_20202\\e-shop\\src\\main\\resources\\static\\images\\");
    }

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
        registry.addViewController("/products/{productUrl}").setViewName("user/product");

        registry.addViewController("/sales/order/history").setViewName("user/order-manager");
        registry.addViewController("/sales/order/view/{orderId}").setViewName("user/order-detail");
        registry.addViewController("/sales/order/tracking/{orderId}").setViewName("user/order-tracking");

        registry.addViewController("/admin").setViewName("admin/dashboard");
        registry.addViewController("/admin/manager_users").setViewName("admin/manager_users");
        registry.addViewController("/admin/manager_users/user/{userId}").setViewName("admin/manager-userDetail");
        registry.addViewController("/admin/manager_users/user/{userId}/orderHistory").setViewName("admin/manager-orderHistory");
        registry.addViewController("/admin/manager_users/user/{userId}/maxOrderHistory").setViewName("admin/manager-maxOrderHistory");
        registry.addViewController("/admin/manager_users/user/{userId}/reviews").setViewName("admin/manager-reviewsHistory");
        registry.addViewController("/admin/manager_users/user/{userId}/wishlist").setViewName("admin/manager-wishlist");

        registry.addViewController("/admin/manager_promotions").setViewName("admin/manager-promotion");
        registry.addViewController("/admin/manager_promotions/add").setViewName("admin/manager-addPromotion");
        registry.addViewController("/admin/manager_promotions/edit/{promotionId}").setViewName("admin/manager-updatePromotion");

        registry.addViewController("/admin/manager_products").setViewName("admin/manager-product");
        registry.addViewController("/admin/manager_products/addProduct").setViewName("admin/manager-addProduct");
        registry.addViewController("/admin/manager_products/updateProduct/{productId}").setViewName("admin/manager-updateProduct");
        registry.addViewController("/admin/manager_products/product/{productId}").setViewName("admin/manager-productDetail");

        registry.addViewController("/admin/manager_orders").setViewName("admin/manager-order");
        registry.addViewController("/admin/manager_orders/{orderId}").setViewName("admin/manager-orderDetail");

        registry.addViewController("/logout_success").setViewName("redirect:/");
        registry.addViewController("/forgot_password").setViewName("user/forgot_password_form");
        registry.addViewController("/forgot_password").setViewName("user/forgot_password_form");
    }
}
