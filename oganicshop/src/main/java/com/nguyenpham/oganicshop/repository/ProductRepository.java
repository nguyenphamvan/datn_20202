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

    Optional<Product> findByProductUrl(String productUrl);
    Page findAll(Pageable pageable);

    @Query(value = "SELECT p FROM Product p  WHERE p.productName LIKE %:keyword% OR p.category.categoryName LIKE %:keyword%",
           countQuery = "SELECT COUNT(p) FROM Product p WHERE p.productName LIKE %:keyword% OR p.category.categoryName LIKE %:keyword%")
    Page findProductsByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT p FROM Product p  WHERE (p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl) AND p.supplier.name =:supplierName",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl AND p.supplier.name =:supplierName")
    Page findProductsByCategoryAndSupplier(@Param("categoryUrl") String categoryUrl, @Param("supplierName") String supplierName, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.supplier.name =:supplierName AND (p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.supplier.name =:supplierName AND (p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)")
    Page findProductsByCategoryAndSupplierAndFilterPrice(@Param("categoryUrl") String categoryUrl, @Param("supplierName") String supplierName, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl")
    Page findProductsByCategoryCategoryUrl(@Param("categoryUrl") String categoryUrl, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE (p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE (p.category.categoryUrl =:categoryUrl OR p.category.parent.categoryUrl =:categoryUrl) AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)")
    Page findProductsByCategoryCategoryUrlAndFilterPrice(@Param("categoryUrl") String categoryUrl, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.supplier.name =:supplierName",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.supplier.name =:supplierName")
    Page findProductsBySupplierName(@Param("supplierName") String supplierName, Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.supplier.name =:supplierName AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)",
            countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category.categoryUrl =:categoryUrl AND (p.finalPrice BETWEEN :minPrice AND :maxPrice)")
    Page findProductsBySupplierNameAndFilterPrice(@Param("supplierName") String supplierName, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);
}
