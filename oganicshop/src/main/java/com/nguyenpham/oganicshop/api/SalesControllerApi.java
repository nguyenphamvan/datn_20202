package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesControllerApi {

    private OrderService orderService;

    @Autowired
    public SalesControllerApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/history")
    public ResponseEntity<?> getOrdersHistory(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.getOrdersHistory(user.getId(), pageNum, pageSize));
    }

    @GetMapping("/order/paging")
    public ResponseEntity<?> getTotalOrderPage(@RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.getTotalOrderPage(user.getId(), pageSize));
    }

    @GetMapping("/order/view/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetail(orderId));
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
        response.put("order", orderService.getOrderDetail(orderId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/order/cancel/{orderId}")
    public ResponseEntity<?> cancel(@PathVariable("orderId") long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @GetMapping("/order/product-not-reviewed")
    public ResponseEntity<?> getProductsUnreviewed(@AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("products", orderService.getListProductUnReviewed(user.getId()));
        return ResponseEntity.ok(response);
    }
}
