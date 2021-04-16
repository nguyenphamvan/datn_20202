package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.OrderDtoResponse;
import com.nguyenpham.oganicshop.dto.ShippingAddressDto;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.ReviewService;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountControllerApi {

    private UserService userService;
    private CategoryService categoryService;
    private ReviewService reviewService;

    @Autowired
    public AccountControllerApi(UserService userService, CategoryService categoryService, ReviewService reviewService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
    }

    @GetMapping("/check-password")
    public ResponseEntity<?> checkPassword(@RequestParam("oldPassword") String rawOldPassword) {
        return ResponseEntity.ok(userService.checkOldPassword(rawOldPassword));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfoAccount() {
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("infoAccount", userService.getInfoAccount());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInfoAccount(@RequestBody UserDto userRequest) {
        return ResponseEntity.ok(userService.updateInfoAccount(userRequest));
    }

    @GetMapping("/address")
    public ResponseEntity<?> getInfoShippingAddress() {
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("shippingAddr", userService.getShippingAddress());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/address/create")
    public ResponseEntity<?> addShippingAddress(@RequestBody ShippingAddressDto request) {
        return ResponseEntity.ok(userService.addShippingAddress(request));
    }

    @PutMapping("/address/update")
    public ResponseEntity<?> updateShippingAddress(@RequestBody ShippingAddressDto request) {
        return ResponseEntity.ok(userService.updateShippingAddress(request));
    }

    @DeleteMapping("/address/delete/{addressId}")
    public ResponseEntity<?> deleteShippingAddress(@PathVariable("addressId") long addressId) {
        return ResponseEntity.ok(userService.deleteShippingAddress(addressId));
    }



    @GetMapping("/wishlist")
    public ResponseEntity<?> getMyWishlist() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("wishLists", userService.getWishlists(user));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/wishlist/add/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(userService.addProductToWishlist(productId));
    }

    @DeleteMapping("/wishlist/remove/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(userService.removeProductFromWishlist(productId));
    }

}
