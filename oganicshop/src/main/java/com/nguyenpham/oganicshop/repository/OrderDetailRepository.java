package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.order o WHERE o.user.id =:userId AND od.reviewed = false")
    Set<OrderDetail> findAllByReviewedIsFalse(@Param("userId") long userId);
}
