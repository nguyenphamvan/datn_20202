package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
