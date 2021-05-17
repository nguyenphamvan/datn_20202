package com.nguyenpham.oganicshop.api.admin;

import com.nguyenpham.oganicshop.dto.PromotionDto;
import com.nguyenpham.oganicshop.entity.Promotion;
import com.nguyenpham.oganicshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/promotion")
public class ManagerPromotionApi {

    private PromotionService promotionService;

    @Autowired
    public ManagerPromotionApi(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping("/all")
    List<PromotionDto> getAllPromotion() {
        return promotionService.getAllCoupon();
    }

    @GetMapping("/{couponId}")
    PromotionDto GetPromotionById(@PathVariable("couponId") long couponId) {
        return promotionService.findCouponById(couponId);
    }

    @PostMapping("/add")
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

    @PutMapping("/edit/{couponId}")
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

    @PutMapping("/delete/{couponId}")
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
}
