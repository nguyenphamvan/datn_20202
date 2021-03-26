package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.entity.Discount;
import com.nguyenpham.oganicshop.repository.DiscountRepository;
import com.nguyenpham.oganicshop.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private DiscountRepository discountRepository;

    @Autowired
    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public DiscountDto getDiscount(String code) {
        return null;
    }

    @Override
    public List<Discount> getAllDiscount() {
        return null;
    }

    @Override
    public DiscountDto addNewDiscount(DiscountDto newDiscount) {
        return null;
    }

    @Override
    public DiscountDto editDiscount(String code, DiscountDto discount) {
        return null;
    }

    @Override
    public void deleteDiscount(String code) {

    }
}
