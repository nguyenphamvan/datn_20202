package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.ProductRequestDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Review;
import com.nguyenpham.oganicshop.entity.Supplier;
import com.nguyenpham.oganicshop.repository.CategoryRepository;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.repository.SupplierRepository;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private SupplierRepository supplierRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public ProductResponseDto insertProduct(ProductRequestDto productRequestDto) {
        Product product = productRequestDto.dtoToEntity();
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).get();
        Supplier supplier = supplierRepository.findById(productRequestDto.getSupplierId()).get();
        product.setCategory(category);
        product.setSupplier(supplier);
        try {
            return productRepository.save(product).convertToDtoNotIncludeReviews();
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        return productRepository.findAll().stream().map(product -> product.convertToDto()).collect(Collectors.toList());
    }

    @Override
    public Page<Product> getProductsByCategory(
            String categoryUrl, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
            return productRepository.findProductsByCategoryCategoryUrlAndFilterPrice(categoryUrl, minPrice, maxPrice, pageable);
        }
        return productRepository.findProductsByCategoryCategoryUrl(categoryUrl, pageable);
    }

    @Override
    public Page<Product> getProductsBySupplier(String Supplier, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
            return productRepository.findProductsBySupplierNameAndFilterPrice(Supplier, minPrice, maxPrice, pageable);
        }
        return productRepository.findProductsBySupplierName(Supplier, pageable);
    }

    @Override
    public Page<Product> getProductsByCategoryAndSupplier(String categoryUrl, String supplierName, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
            return productRepository.findProductsByCategoryAndSupplierAndFilterPrice(categoryUrl, supplierName, minPrice, maxPrice, pageable);
        }
        return productRepository.findProductsByCategoryAndSupplier(categoryUrl, supplierName, pageable);
    }

    @Override
    public ProductResponseDto getProduct(String productUrl) {
        Product product = productRepository.findByUrl(productUrl).orElse(null);
        Set<Review> reviews = product.getReviews().stream().filter(rv -> rv.getRootComment() == null).collect(Collectors.toSet());
        product.setReviews(reviews);
        return product.convertToDto();
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Page<Product> searchProductByKeyword(String keyword, int pageNum, int pageSize, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        return productRepository.findProductsByKeyword(keyword, pageable);
    }

    @Override
    public int getAmountAvailable(String productUrl) {
        return productRepository.findByUrl(productUrl).get().getAmount();
    }

    @Override
    public boolean isProvideEnoughQuantity(String productUrl, int quantity) {
        int quantityAvailable = productRepository.findByUrl(productUrl).get().getAmount();
        if (quantityAvailable >= quantity) {
            return true;
        }
        return false;
    }
}
