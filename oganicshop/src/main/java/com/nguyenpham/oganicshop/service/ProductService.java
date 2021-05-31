package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.entity.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    long countNumberProduct();
    List<ProductResponse> getAllProduct();
    Page<Product> getProductsByCategory(String categoryUrl, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> getProductsByKeyword(String keyword, int pageNum, int pageSize, String sortField, String sortDir);
    int getAmountAvailable(String productUrl);
    boolean isProvideEnoughQuantity(String productUrl, int quantity);
    ProductResponse getProductByUrl(String productUrl);
    Product getProductDetail(Long productId);
    ProductResponse addProduct(ProductRequest productRequest) throws IOException;
    ProductResponse updateProduct(ProductRequest productRequest) throws IOException;
    int importProduct(long productId, int amount);
    boolean stopBusinessProduct(long productId);
    boolean openBusinessProduct(long productId);
}
