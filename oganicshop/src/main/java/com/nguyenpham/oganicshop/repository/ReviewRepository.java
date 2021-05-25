package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByUserId(long userId, Pageable pageable);
    List<Review> findReviewsByUserId(long userId);
}
