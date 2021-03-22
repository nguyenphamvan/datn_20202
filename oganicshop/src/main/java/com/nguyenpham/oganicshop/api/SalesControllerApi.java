package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.OrderDetailDto;
import com.nguyenpham.oganicshop.dto.OrderLoggingDto;
import com.nguyenpham.oganicshop.entity.OrderDetail;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SalesControllerApi {

    private OrderService orderService;

    @Autowired
    public SalesControllerApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/history")
    public ResponseEntity<?> getOrdersHistory(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(orderService.getListOrderHistory(user.getId(), pageNum, pageSize));
    }

    @GetMapping("/order/paging")
    public ResponseEntity<?> getTotalOrderPage(@RequestParam("pageSize") int pageSize) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(orderService.getTotalOrderPage(user.getId(), pageSize));
    }

    @GetMapping("/order/view/{orderId}")
    public ResponseEntity<?> getSingleOrderById(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId).convertOrderToOrderDto());
    }

    @GetMapping("/order/{orderId}/list-item")
    public ResponseEntity<?> getListOrderItem(@PathVariable("orderId") long orderId) {
        Set<OrderDetailDto> orderDetailDtos = orderService.getOrderById(orderId).getOrderDetails().stream()
                .map(od -> od.convertOrderDetailToOrderDetailDto())
                .collect(Collectors.toSet());
        return ResponseEntity.ok(orderDetailDtos);
    }

    @GetMapping("/order/tracking/{orderId}")
    public ResponseEntity<?> getOrderTracking(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId).convertOrderLoggingToOrderLoggingDto());
    }

    @GetMapping("/order/product-not-reviewed")
    public ResponseEntity<?> getListProductNotReviewed() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(orderService.getListProductNotReviewed( user.getId()));
    }
}
