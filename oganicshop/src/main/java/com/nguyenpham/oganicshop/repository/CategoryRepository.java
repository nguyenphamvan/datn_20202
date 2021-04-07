package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();
    Optional<Category> findByCategoryUrl(String categoryUrl);
}
