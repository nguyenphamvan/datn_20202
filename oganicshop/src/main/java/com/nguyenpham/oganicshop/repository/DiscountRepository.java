package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

    Discount findByCode(String code);
}
