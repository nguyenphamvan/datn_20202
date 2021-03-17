package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SalesControllerApi {

    private OrderService orderService;

    @Autowired
    public SalesControllerApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/history")
    public ResponseEntity<?> getOrderHistory(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(orderService.getListOrderHistory(user.getId(), pageNum, pageSize));
    }

    @GetMapping("/order/paging")
    public ResponseEntity<?> getTotalOrderPage(@RequestParam("pageSize") int pageSize) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return ResponseEntity.ok(orderService.getTotalOrderPage(user.getId(), pageSize));
    }
}
