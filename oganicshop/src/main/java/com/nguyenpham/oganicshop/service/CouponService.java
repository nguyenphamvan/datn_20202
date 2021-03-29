package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.Discount;

public interface CouponService {
    Discount findCoupon(String couponCode);
}
