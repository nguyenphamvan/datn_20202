package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.entity.Discount;
import com.nguyenpham.oganicshop.repository.DiscountRepository;
import com.nguyenpham.oganicshop.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl implements CouponService {

    private DiscountRepository discountRepository;

    @Autowired
    public CouponServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount findCoupon(String couponCode) {
        return discountRepository.findByCode(couponCode);
    }
}
