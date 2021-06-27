package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductUrl(String productUrl);
    Product findTopByOrderByIdDesc();
    List<Product> findAll();
    Product findByBookId(long bookId);

    @Query(value = "SELECT p FROM Product p  WHERE (p.title LIKE %:keyword% OR p.category.categoryName LIKE %:keyword% OR p.authors LIKE %:keyword%)",
           countQuery = "SELECT COUNT(p) FROM Product p WHERE (p.title LIKE %:keyword% OR p.category.categoryName LIKE %:keyword% OR p.authors LIKE %:keyword%)")
    Page findProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT p FROM Product p  WHERE (p.title LIKE %:keyword% OR p.category.categoryName LIKE %:keyword% OR p.authors LIKE %:keyword%) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE (p.title LIKE %:keyword% OR p.category.categoryName LIKE %:keyword% OR p.authors LIKE %:keyword%) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)")
    Page findProductsByKeywordAndPrice(@Param("keyword") String keyword, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.category.id =:categoryId ",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category.id =:categoryId")
    Page findProductsByCategoryId(@Param("categoryId") long categoryId, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE (p.category.id =:categoryId) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE (p.category.id =:categoryId) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)")
    Page findProductsByCategoryCategoryAndFilterPrice(@Param("categoryId") long categoryId, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);
}
