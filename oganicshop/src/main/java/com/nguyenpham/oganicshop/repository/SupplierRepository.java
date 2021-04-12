package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Supplier findSupplierByName(String supplierName);

    @Query("SELECT DISTINCT sp FROM Supplier sp JOIN FETCH sp.products p JOIN FETCH p.category c JOIN FETCH c.parent cp WHERE cp.categoryUrl = :categoryUrl")
    List<Supplier> findAllByCategoryUrl(@Param("categoryUrl") String categoryUrl);
}
