package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Rating;
import com.nguyenpham.oganicshop.entity.RatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    boolean existsById(RatingKey key);
}
