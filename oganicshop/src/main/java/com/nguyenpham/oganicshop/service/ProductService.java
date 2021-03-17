package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Page<Product> getProductsByCategory(String categoryUrl, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> getProductsBySupplier(String Supplier, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> getProductsByCategoryAndSupplier(String categoryUrl, String supplierName, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> searchProductByKeyword(String keyword, int pageNum, int pageSize, String sortField, String sortDir);
    Product getProduct(String productUrl);
    Product getProductById(Long productId);
}
