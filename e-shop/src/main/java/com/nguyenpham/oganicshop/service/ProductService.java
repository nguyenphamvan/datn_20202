package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.entity.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> findAll(List<Long> listId);
    List<ProductResponse> getProductRecommend(List<Long> listBookIds);
    Product save(Product product);
    long countNumberProduct();
    List<ProductResponse> getAllProduct();
    Page<Product> getProductsByCategory(long categoryId, double minPrice, double maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    Page<Product> getProductsByKeyword(String keyword, double minPrice, double maxPrice, int pageNum, int pageSize, String sortField, String sortDir);
    int getAmountAvailable(String productUrl);
    boolean isProvideEnoughQuantity(String productUrl, int quantity);
    ProductResponse getProductById(long productId);
    ProductResponse getProductByUrl(String productUrl);
    Product getProductDetail(Long productId);
    ProductResponse addProduct(ProductRequest productRequest) throws IOException;
    ProductResponse updateProduct(ProductRequest productRequest) throws IOException;
    int importProduct(long productId, int amount);
    boolean stopBusinessProduct(long productId);
    boolean openBusinessProduct(long productId);
}
