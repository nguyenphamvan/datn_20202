package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByUserId(long userId);
    List<Review> findAllByProductIdAndRootCommentIsNull(long productId);
}
