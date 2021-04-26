package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.dto.PromotionDto;
import com.nguyenpham.oganicshop.entity.Promotion;

import java.util.List;

public interface PromotionService {
    long countNumberCoupon();
    PromotionDto findCouponById(long couponId);
    Promotion findCoupon(String couponCode);
    List<Promotion> getAllCouponForOrder(int orderValue);
    List<PromotionDto> getAllCoupon();
    Promotion addNewCoupon(PromotionDto newPromotion);
    PromotionDto editCoupon(PromotionDto PromotionRequest);
    void deleteCoupon(long promotionId);
}
