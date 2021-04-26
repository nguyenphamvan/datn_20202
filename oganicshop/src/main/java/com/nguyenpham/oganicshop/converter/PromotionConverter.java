package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.PromotionDto;
import com.nguyenpham.oganicshop.entity.Promotion;

public class PromotionConverter implements GeneralConverter<Promotion, PromotionDto, PromotionDto>{
    @Override
    public PromotionDto entityToDto(Promotion promotion) {
        PromotionDto promotionDto = new PromotionDto();
        promotionDto.setId(promotion.getId());
        promotionDto.setCode(promotion.getCode());
        promotionDto.setTitle(promotion.getTitle());
        promotionDto.setDiscountPercent(promotion.getDiscountPercent());
        promotionDto.setDiscountPrice(promotion.getDiscountPrice());
        promotionDto.setMaxDiscountValue(promotion.getMaxDiscountValue());
        promotionDto.setMinOrderValue(promotion.getMinOrderValue());
        promotionDto.setStartDate(promotion.getStartDate());
        promotionDto.setEndDate(promotion.getEndDate());
        promotionDto.setNumberOfUses(promotion.getNumberOfUses());

        return promotionDto;
    }

    @Override
    public Promotion dtoToEntity(PromotionDto promotionDto) {
        return null;
    }

    public Promotion dtoToEntity(Promotion promotion, PromotionDto promotionDto) {
        promotion.setTitle(promotionDto.getTitle());
        promotion.setCode(promotionDto.getCode());
        promotion.setDiscountPercent(promotionDto.getDiscountPercent());
        promotion.setDiscountPrice(promotionDto.getDiscountPrice());
        promotion.setMaxDiscountValue(promotionDto.getMaxDiscountValue());
        promotion.setMinOrderValue(promotionDto.getMinOrderValue());
        promotion.setEndDate(promotion.getEndDate());
        promotion.setStartDate(promotion.getStartDate());
        promotion.setNumberOfUses(promotionDto.getNumberOfUses());
        return promotion;
    }


}
