package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.converter.PromotionConverter;
import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.dto.PromotionDto;
import com.nguyenpham.oganicshop.entity.Promotion;
import com.nguyenpham.oganicshop.repository.PromotionRepository;
import com.nguyenpham.oganicshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    private PromotionRepository promotionRepository;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    @Override
    public long countNumberCoupon() {
        return promotionRepository.count();
    }

    @Override
    public PromotionDto findCouponById(long couponId) {
        return new PromotionConverter().entityToDto(promotionRepository.findById(couponId).get());
    }

    @Override
    public Promotion findCoupon(String couponCode) {
        return promotionRepository.findByCode(couponCode);
    }

    @Override
    public List<Promotion> getAllCouponForOrder(int orderValue) {
        return promotionRepository.findAllByMinOrderValueLessThan(orderValue);
    }

    @Override
    public List<PromotionDto> getAllCoupon() {
        PromotionConverter promotionConverter = new PromotionConverter();
        return promotionRepository.findAll().stream().map(p -> promotionConverter.entityToDto(p)).collect(Collectors.toList());
    }

    @Override
    public Promotion addNewCoupon(PromotionDto newDiscount) {
        return null;
    }

    @Override
    public PromotionDto editCoupon(PromotionDto PromotionRequest) {
        PromotionConverter converter = new PromotionConverter();
        Promotion promotion = promotionRepository.findById(PromotionRequest.getId()).get();
        promotion = converter.dtoToEntity(promotion, PromotionRequest);
        return converter.entityToDto(promotionRepository.save(promotion));
    }

    @Override
    public void deleteCoupon(long promotionId) {
        promotionRepository.deleteById(promotionId);
    }
}
