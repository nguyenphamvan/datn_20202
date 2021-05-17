package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface OrderDetailRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT od FROM OrderItem od JOIN FETCH od.order o WHERE o.user.id =:userId AND od.reviewed = false")
    Set<OrderItem> findAllByReviewedIsFalse(@Param("userId") long userId);

    @Query("SELECT od FROM OrderItem od JOIN FETCH od.order o WHERE o.user.id =:userId AND od.reviewed = false AND od.product.id = :productId")
    Set<OrderItem> findAllByUnReviewedOfUser(@Param("userId") long userId, @Param("productId") long productId);

    int countByProductId(long productId);
}
