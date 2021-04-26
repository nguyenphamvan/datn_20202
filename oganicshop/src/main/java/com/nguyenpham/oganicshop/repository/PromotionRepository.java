package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Promotion findByCode(String code);
    List<Promotion> findAllByMinOrderValueLessThan(int orderValue);
}
