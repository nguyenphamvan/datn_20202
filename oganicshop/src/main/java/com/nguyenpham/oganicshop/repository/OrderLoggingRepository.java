package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.OrderLogging;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderLoggingRepository extends JpaRepository<OrderLogging, Long> {
    Set<OrderLogging> findAllByOrderIdOrderByUpdateTimeAsc(long orderId);
}
