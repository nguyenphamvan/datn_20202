package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.entity.Discount;

import java.util.List;

public interface DiscountService {

    DiscountDto getDiscount(String code);
    List<Discount> getAllDiscount();
    DiscountDto addNewDiscount(DiscountDto newDiscount);
    DiscountDto editDiscount(String code, DiscountDto discount);
    void deleteDiscount(String code);


}
