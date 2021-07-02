package com.nguyenpham.oganicshop.controller;

import com.nguyenpham.oganicshop.dto.PromotionDto;
import com.nguyenpham.oganicshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PromotionController {

    private PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/api/admin/promotion/all")
    List<PromotionDto> getAllPromotion() {
        return promotionService.getAllCoupon();
    }

    @GetMapping("/api/admin/promotion/{promotionId}")
    PromotionDto GetPromotionById(@PathVariable("promotionId") long promotionId) {
        return promotionService.findCouponById(promotionId);
    }

    @PostMapping("/api/admin/promotion/add")
    @PreAuthorize("hasRole('ADMIN')")
    Object addPromotion(@ModelAttribute PromotionDto promotion) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", promotionService.addNewCoupon(promotion));
            response.put("statusCode", HttpStatus.OK);
            response.put("status", true);
        } catch (Exception e) {
            response.put("data", null);
            response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("status", false);
        }
        return response;
    }

    @PutMapping("/api/admin/promotion/edit/{couponId}")
    @PreAuthorize("hasRole('ADMIN')")
    Object EditPromotion(@PathVariable("couponId") long couponId, @ModelAttribute PromotionDto promotion) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", promotionService.editCoupon(promotion));
            response.put("statusCode", HttpStatus.OK);
            response.put("status", true);
        } catch (Exception e) {
            response.put("data", null);
            response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("status", false);
        }
        return response;
    }

    @PutMapping("/api/admin/promotion/delete/{couponId}")
    @PreAuthorize("hasRole('ADMIN')")
    Object deletePromotion(@PathVariable("couponId") long couponId) {
        Map<String, Object> response = new HashMap<>();
        response.put("data", null);
        try {
            promotionService.deleteCoupon(couponId);
            response.put("statusCode", HttpStatus.OK);
            response.put("status", true);
        } catch (Exception e) {
            response.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);
            response.put("status", false);
        }
        return response;
    }


    @GetMapping("/api/promotion/cache/{promotionId}")
    public boolean cachePromotion(@PathVariable("promotionId") long promotionId, HttpSession session) {
        return promotionService.cacheMyPromotion(session, promotionId);
    }
}
