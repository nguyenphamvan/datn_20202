package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ProductRequestDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.entity.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    long countNumberProduct();
    List<ProductResponseDto> getAllProduct();
    Page<Product> getProductsByCategory(String categoryUrl, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> getProductsBySupplier(String Supplier, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> getProductsByCategoryAndSupplier(String categoryUrl, String supplierName, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> searchProductByKeyword(String keyword, int pageNum, int pageSize, String sortField, String sortDir);
    int getAmountAvailable(String productUrl);
    boolean isProvideEnoughQuantity(String productUrl, int quantity);
    ProductResponseDto getProduct(String productUrl);
    Product getProductById(Long productId);
    ProductResponseDto insertProduct(ProductRequestDto productRequestDto) throws IOException;
    ProductResponseDto editProduct(ProductRequestDto productRequestDto) throws IOException;
    int importProduct(long productId, int amount);
    boolean stopBusinessProduct(long productId);
    boolean openBusinessProduct(long productId);
}
