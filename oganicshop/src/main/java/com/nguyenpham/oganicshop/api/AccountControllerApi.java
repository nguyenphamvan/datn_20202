package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.converter.UserConverter;
import com.nguyenpham.oganicshop.dto.AddressRequest;
import com.nguyenpham.oganicshop.dto.OrderResponse;
import com.nguyenpham.oganicshop.dto.UserRequest;
import com.nguyenpham.oganicshop.dto.UserResponse;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class AccountControllerApi {

    private UserConverter userConverter;
    private UserService userService;
    private OrderService orderService;
    private ReviewService reviewService;

    @Autowired
    public AccountControllerApi(UserConverter userConverter, UserService userService, OrderService orderService, ReviewService reviewService) {
        this.userConverter = userConverter;
        this.userService = userService;
        this.orderService = orderService;
        this.reviewService = reviewService;
    }

    @GetMapping("/api/admin/account/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllAccount(@AuthenticationPrincipal MyUserDetail myUserDetail) throws Exception {
        try {
            return ResponseEntity.ok(userService.getAllAccount());
        } catch (Exception e) {
            throw new Exception("Not found");
        }
    }

    @GetMapping("/api/admin/account/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getInfoDetailAccount(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(userService.getInfoDetailAccount(userId));
    }

    @GetMapping("/api/admin/account/{userId}/wishlist")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserWishlist(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("user", userConverter.entityToDto(user));
        response.put("wishLists", userService.getWishlists(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin/account/{userId}/reviews")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccountReviews(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("user", userConverter.entityToDto(user));
        response.put("reviews", reviewService.getListReviews(user));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin/account/{userId}/maxOrderHistory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserMaxOrdersHistory(@PathVariable("userId") long userId) {
        Map<String, Object> response = new HashMap<>();
        UserResponse user = userConverter.entityToDto(userService.findUserById(userId));
        response.put("user", user);
        OrderResponse maxOrderHistory = orderService.getAllOrderByUserId(userId).stream().max(Comparator.comparing(OrderResponse::getTotal)).orElseThrow(NoSuchElementException::new);
        maxOrderHistory = orderService.getOrderDetail(maxOrderHistory.getId());
        response.put("maxOrderHistory", maxOrderHistory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/admin/account/{userId}/orderHistory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserOrdersHistory(@PathVariable("userId") long userId) {
        Map<String, Object> response = new HashMap<>();
        UserResponse user = userConverter.entityToDto(userService.findUserById(userId));
        response.put("user", user);
        response.put("ordersHistory", orderService.getAllOrderByUserId(userId));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/admin/account/{userId}/updateRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setRole(@PathVariable("userId") long userId, @RequestParam("role") String role) {
        return ResponseEntity.ok(userService.setRoleAccount(userId, role));
    }

    @PutMapping("/api/admin/account/{userId}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockAccount(@PathVariable("userId") long userId) {
        userService.doBlockAccount(userId, true);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/api/admin/account/{userId}/unBlock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unBlockAccount(@PathVariable("userId") long userId) {
        userService.doBlockAccount(userId, false);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/api/account/check-password")
    public ResponseEntity<?> checkPassword(@RequestParam("oldPassword") String rawOldPassword) {
        return ResponseEntity.ok(userService.checkOldPassword(rawOldPassword));
    }

    @GetMapping("/api/account/info")
    public ResponseEntity<?> getInfoAccount() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("userInfo", userService.getInfoAccount());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/account/update")
    public ResponseEntity<?> updateInfoAccount(@RequestBody UserRequest userRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("status", true);
            response.put("userInfo", userService.updateInfoAccount(userRequest));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", false);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/account/address")
    public ResponseEntity<?> getListAddress() {
        return ResponseEntity.ok(userService.getListAddress());
    }

    @PostMapping("/api/account/address/create")
    public ResponseEntity<?> addNewAddress(@RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.addAddress(request));
    }

    @PutMapping("/api/account/address/update")
    public ResponseEntity<?> updateOldAddress(@RequestBody AddressRequest request) {
        return ResponseEntity.ok(userService.updateAddress(request));
    }

    @DeleteMapping("/api/account/address/delete/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("addressId") long addressId) {
        return ResponseEntity.ok(userService.deleteAddress(addressId));
    }

    @GetMapping("/api/account/wishlist")
    public ResponseEntity<?> getMyWishlist() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("wishLists", userService.getWishlists(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/account/wishlist/add/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(userService.addProductToWishlist(productId));
    }

    @DeleteMapping("/api/account/wishlist/remove/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(userService.removeProductFromWishlist(productId));
    }

}
