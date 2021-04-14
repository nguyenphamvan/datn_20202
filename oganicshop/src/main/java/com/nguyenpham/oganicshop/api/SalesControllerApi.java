package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderLoggingDto;
import com.nguyenpham.oganicshop.entity.OrderDetail;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SalesControllerApi {

    private OrderService orderService;
    private CategoryService categoryService;

    @Autowired
    public SalesControllerApi(OrderService orderService, CategoryService categoryService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
    }

    @GetMapping("/order/history")
    public ResponseEntity<?> getOrdersHistory(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("orders", orderService.getListOrderHistory(user.getId(), pageNum, pageSize));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/paging")
    public ResponseEntity<?> getTotalOrderPage(@RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.getTotalOrderPage(user.getId(), pageSize));
    }

    @GetMapping("/order/view/{orderId}")
    public ResponseEntity<?> getSingleOrderById(@PathVariable("orderId") long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("order", orderService.getOrderById(user.getId(), orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}/list-item")
    public ResponseEntity<?> getListOrderItem(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("orderItems", orderService.getListOrderItem(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/tracking/{orderId}")
    public ResponseEntity<?> getOrderTracking(@PathVariable("orderId") long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("order", orderService.getOrderById(user.getId(), orderId));
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
        response.put("categories", categoryService.getListCategory());
        response.put("products", orderService.getListProductNotReviewed(user.getId()));
        return ResponseEntity.ok(response);
    }
}
