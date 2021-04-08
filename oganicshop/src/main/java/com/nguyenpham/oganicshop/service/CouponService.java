package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.entity.Discount;

import java.util.List;

public interface CouponService {
    Discount findCoupon(String couponCode);
    Discount getDiscount(String code);
    List<Discount> getAllDiscountForOrder(int orderValue);
    List<Discount> getAllDiscount();
    Discount addNewDiscount(DiscountDto newDiscount);
    Discount editDiscount(String code, DiscountDto discount);
    void deleteDiscount(String code);
}
