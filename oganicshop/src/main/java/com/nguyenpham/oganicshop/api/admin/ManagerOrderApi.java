package com.nguyenpham.oganicshop.api.admin;

import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/order/")
public class ManagerOrderApi {

    private UserService userService;
    private OrderService orderService;

    @Autowired
    public ManagerOrderApi(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrderDetailHistory(@PathVariable("orderId") long orderId) {
        Map<String, Object> response = new HashMap<>();
        Order order = orderService.getOrderById(orderId);
        UserDto user = order.getUser().convertUserToUserDto();
        response.put("user", user);
        OrderDtoResponse orderDtoResponse = order.convertOrderToOrderDto();
        orderDtoResponse.setMessage(null);
        response.put("order", orderDtoResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllOrder() {
        List<OrderDtoResponse> order = orderService.getAll();
        return ResponseEntity.ok(order);
    }
}
