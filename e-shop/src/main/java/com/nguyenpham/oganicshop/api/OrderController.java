package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.OrderResponse;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/admin/order/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrder() {
        List<OrderResponse> order = orderService.getAll();
        return ResponseEntity.ok(order);
    }

    @GetMapping("/api/admin/order/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getOrderDetail(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        OrderResponse order = orderService.getOrderDetail(orderId);
        order.setMessage(null);
        response.put("order", order);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/admin/order/updateStatus/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean updateStatus(@PathVariable("orderId") long orderId, @RequestBody ObjectNode objectNode) {
        int statusId = objectNode.get("status").asInt();
        String message = objectNode.get("message").asText();
        return orderService.updatedOrderStatus(orderId, statusId, message);
    }

    @PutMapping("/api/order/cancel/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> cancel(@PathVariable("orderId") long orderId, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @GetMapping("/api/order/history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrdersHistory(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.getOrdersHistory(user.getId(), pageNum, pageSize));
    }

    @GetMapping("/api/order/paging")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTotalOrderPage(@RequestParam("pageSize") int pageSize, @AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        return ResponseEntity.ok(orderService.countTotalOrderPage(user.getId(), pageSize));
    }

    @GetMapping("/api/order/view/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrderDetail1(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetail(orderId));
    }

    @GetMapping("/api/order/{orderId}/list-item")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getListOrderItem(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("orderItems", orderService.getListOrderItem(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/tracking/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrderTracking(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        response.put("order", orderService.getOrderDetail(orderId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/order/product-not-reviewed")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getProductsUnreviewed(@AuthenticationPrincipal MyUserDetail myUserDetail) {
        User user = myUserDetail.getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("products", orderService.getListProductUnReviewed(user.getId()));
        return ResponseEntity.ok(response);
    }
}
