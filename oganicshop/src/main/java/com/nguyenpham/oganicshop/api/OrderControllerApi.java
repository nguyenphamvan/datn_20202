package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.UserConverter;
import com.nguyenpham.oganicshop.dto.OrderResponse;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/order/")
public class OrderControllerApi {

    private OrderService orderService;

    @Autowired
    public OrderControllerApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllOrder() {
        List<OrderResponse> order = orderService.getAll();
        return ResponseEntity.ok(order);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        OrderResponse order = orderService.getOrderDetail(orderId);
        order.setMessage(null);
        response.put("order", order);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateStatus/{orderId}")
    public boolean updateStatus(@PathVariable("orderId") long orderId, @RequestBody ObjectNode objectNode) {
        int statusId = objectNode.get("status").asInt();
        String message = objectNode.get("message").asText();
        return orderService.updatedOrderStatus(orderId, statusId, message);
    }
}
