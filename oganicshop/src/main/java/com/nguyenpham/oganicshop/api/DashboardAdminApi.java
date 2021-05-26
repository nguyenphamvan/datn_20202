package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ProductService;
import com.nguyenpham.oganicshop.service.PromotionService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/dashboard")
public class DashboardAdminApi {

    private UserService userService;
    private ProductService productService;
    private OrderService orderService;
    private PromotionService promotionService;

    public DashboardAdminApi(UserService userService, ProductService productService, OrderService orderService, PromotionService promotionService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.promotionService = promotionService;
    }

    @GetMapping("/basicInfo")
    public Object getBasicInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("numberOfProducts", productService.countNumberProduct());
        response.put("numberOfAccounts", userService.countNumberAccount());
        response.put("numberOfOrders", orderService.countNumberOrder());
        response.put("numberOfPromotions", promotionService.countNumberCoupon());
        return response;
    }
}
