package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.entity.Discount;
import com.nguyenpham.oganicshop.repository.DiscountRepository;
import com.nguyenpham.oganicshop.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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

    @Override
    public Discount getDiscount(String code) {
        return null;
    }

    @Override
    public List<Discount> getAllDiscountForOrder(int orderValue) {
        return discountRepository.findAllByMinOrderValueLessThan(orderValue);
    }

    @Override
    public List<Discount> getAllDiscount() {
        return null;
    }

    @Override
    public Discount addNewDiscount(DiscountDto newDiscount) {
        return null;
    }

    @Override
    public Discount editDiscount(String code, DiscountDto discount) {
        return null;
    }

    @Override
    public void deleteDiscount(String code) {

    }
}
