package com.nguyenpham.oganicshop.api.admin;

import com.nguyenpham.oganicshop.converter.UserConverter;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.dto.UserResponseDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/account")
public class ManagerAccountApi {

    private UserConverter userConverter;
    private UserService userService;
    private OrderService orderService;
    private ReviewService reviewService;

    @Autowired
    public ManagerAccountApi(UserConverter userConverter, UserService userService, OrderService orderService, ReviewService reviewService) {
        this.userConverter = userConverter;
        this.userService = userService;
        this.orderService = orderService;
        this.reviewService = reviewService;
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(@AuthenticationPrincipal MyUserDetail myUserDetail) throws Exception {
        Map<String, Object> response = new HashMap<>();
        User user = myUserDetail.getUser();
        try {
            return ResponseEntity.ok(userService.getAllUser());
        } catch (Exception e) {
            throw new Exception("Not found");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getInfoDetailUser(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(userService.getInfoDetailUser(userId));
    }

    @GetMapping("{userId}/wishlist")
    public ResponseEntity<?> getMyWishlist(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("user", userConverter.entityToDto(user));
        response.put("wishLists", userService.getWishlists(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<?> getAccountReviews(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("user", userConverter.entityToDto(user));
        response.put("reviews", reviewService.getListReviews(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping("{userId}/maxOrderHistory")
    public ResponseEntity<?> getUserMaxOrdersHistory(@PathVariable("userId") long userId) {
        Map<String, Object> response = new HashMap<>();
        UserResponseDto user = userConverter.entityToDto(userService.findUserById(userId));
        response.put("user", user);
        OrderDtoResponse maxOrderHistory = orderService.getAllOrderByUserId(userId).stream().max(Comparator.comparing(OrderDtoResponse::getTotal)).orElseThrow(NoSuchElementException::new);
        maxOrderHistory = orderService.getOrderById(maxOrderHistory.getId());
        response.put("maxOrderHistory", maxOrderHistory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{userId}/orderHistory")
    public ResponseEntity<?> getUserOrdersHistory(@PathVariable("userId") long userId) {
        Map<String, Object> response = new HashMap<>();
        UserResponseDto user = userConverter.entityToDto(userService.findUserById(userId));
        response.put("user", user);
        response.put("ordersHistory", orderService.getAllOrderByUserId(userId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("{userId}/updateRole")
    public ResponseEntity<?> updateRole(@PathVariable("userId") long userId, @RequestParam("role") String role) {
        return ResponseEntity.ok(userService.updateRoleUser(userId, role));
    }

    @PutMapping("{userId}/block")
    public ResponseEntity<?> blockAccount(@PathVariable("userId") long userId) {
        userService.doBlockAccount(userId, true);
        return ResponseEntity.ok(true);
    }

    @PutMapping("{userId}/unBlock")
    public ResponseEntity<?> unBlockAccount(@PathVariable("userId") long userId) {
        userService.doBlockAccount(userId, false);
        return ResponseEntity.ok(true);
    }


}