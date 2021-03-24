package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountControllerApi {

    private UserService userService;

    @Autowired
    public AccountControllerApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check-password")
    public ResponseEntity<?> checkPassword(@RequestParam("oldPassword") String rawOldPassword) {
        return ResponseEntity.ok(userService.checkOldPassword(rawOldPassword));
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfoAccount() {
        return ResponseEntity.ok(userService.getInfoAccount());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateInfoAccount(@RequestBody UserDto userRequest) {
        return ResponseEntity.ok(userService.updateInfo(userRequest));
    }

    @GetMapping("/address")
    public ResponseEntity<?> getInfoShippingAddress() {
        UserDto userUpdated = userService.getInfoAccount();
        Map<String, Object> response = new HashMap<>();
        response.put("name", userUpdated.getFullName());
        response.put("address", userUpdated.getAddress());
        response.put("phone", userUpdated.getPhone());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/address/update")
    public ResponseEntity<?> updateInfoShippingAddress(@RequestBody ObjectNode object) {
        String newAddress = object.get("address").asText();
        String phone = object.get("phone").asText();
        UserDto userUpdated = userService.updateShippingAddress(newAddress, phone);
        Map<String, Object> response = new HashMap<>();
        response.put("name", userUpdated.getFullName());
        response.put("address", userUpdated.getAddress());
        response.put("phone", userUpdated.getPhone());
        return ResponseEntity.ok(response);
    }

    @GetMapping("my-review")
    public ResponseEntity<?> getMyReviews() {
        return null;
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> getMyWishlist() {
        return ResponseEntity.ok(userService.getWishlists());
    }

    @PostMapping("/wishlist/add/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(userService.addProductToWishlist(productId));
    }

    @DeleteMapping("/wishlist/remove/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable("productId") long productId) {
        return ResponseEntity.ok(userService.removeProductFromWishlist(productId));
    }

    @GetMapping("/notification")
    public ResponseEntity<?> getMyNotifications() {
        return null;
    }

}
