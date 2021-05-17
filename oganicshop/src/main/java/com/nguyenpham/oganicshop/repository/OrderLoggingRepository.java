package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderLoggingRepository extends JpaRepository<OrderStatus, Long> {
    Set<OrderStatus> findAllByOrderIdOrderByUpdateTimeAsc(long orderId);
}
