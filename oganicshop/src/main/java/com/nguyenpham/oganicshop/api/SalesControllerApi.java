package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesControllerApi {

    private UserService userService;
    private OrderService orderService;
    private CategoryService categoryService;

    @Autowired
    public SalesControllerApi(UserService userService, OrderService orderService, CategoryService categoryService) {
        this.userService = userService;
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping("/order/history")
    public ResponseEntity<?> getOrdersHistory(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.getListOrderHistory(user.getId(), pageNum, pageSize));
    }

    @GetMapping("/order/paging")
    public ResponseEntity<?> getTotalOrderPage(@RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.getTotalOrderPage(user.getId(), pageSize));
    }

    @GetMapping("/order/view/{orderId}")
    public ResponseEntity<?> getSingleOrderById(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/order/{orderId}/list-item")
    public ResponseEntity<?> getListOrderItem(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("orderItems", orderService.getListOrderItem(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/tracking/{orderId}")
    public ResponseEntity<?> getOrderTracking(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("order", orderService.getOrderById(orderId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<?> cancel(@PathVariable("orderId") long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.cancelOrder(user.getId(), orderId));
    }

    @GetMapping("/order/product-not-reviewed")
    public ResponseEntity<?> getListProductNotReviewed(@AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("products", orderService.getListProductNotReviewed(user.getId()));
        return ResponseEntity.ok(response);
    }
}
