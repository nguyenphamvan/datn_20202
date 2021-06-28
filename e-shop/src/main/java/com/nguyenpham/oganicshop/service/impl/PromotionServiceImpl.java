package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.converter.PromotionConverter;
import com.nguyenpham.oganicshop.dto.DiscountDto;
import com.nguyenpham.oganicshop.dto.PromotionDto;
import com.nguyenpham.oganicshop.entity.CartItem;
import com.nguyenpham.oganicshop.entity.Promotion;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.repository.PromotionRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return promotionRepository.findAllByStartDateLessThanAndEndDateGreaterThan(now, now).stream().map(p -> promotionConverter.entityToDto(p)).collect(Collectors.toList());
    }

    @Override
    public PromotionDto addNewCoupon(PromotionDto newDiscount) {
        PromotionConverter converter = new PromotionConverter();
        Promotion promotion = converter.dtoToEntity(newDiscount);
        return converter.entityToDto(promotionRepository.save(promotion));
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

    @Override
    public boolean cacheMyPromotion(HttpSession session, long promotionId) {
        HashMap<Long, Promotion> myPromotion = (HashMap<Long, Promotion>) session.getAttribute(Constant.PROMOTION_SESSION);
        if (myPromotion == null) {
            myPromotion = new HashMap<>();
        }
        try {
            if (!myPromotion.containsKey(promotionId)) {
                Promotion promotion = promotionRepository.findById(promotionId).get();
                myPromotion.put(promotionId, promotion);
                session.setAttribute(Constant.PROMOTION_SESSION, myPromotion);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<PromotionDto> getMyPromotion(HttpSession session, User user) {
        HashMap<Long, Promotion> myPromotions = (HashMap<Long, Promotion>) session.getAttribute(Constant.PROMOTION_SESSION);
        if (myPromotions == null) {
            myPromotions = new HashMap<>();
            if (user.getOrders().size() == 0) {
                Promotion promotionFirstOrder = findCoupon("FIRSTORDER50");
                myPromotions.put(promotionFirstOrder.getId(), promotionFirstOrder);
            }
        }
        PromotionConverter promotionConverter = new PromotionConverter();
        List<PromotionDto> promotionDtos = myPromotions.values().stream().map(p -> promotionConverter.entityToDto(p)).collect(Collectors.toList());
        return promotionDtos;
    }


}
