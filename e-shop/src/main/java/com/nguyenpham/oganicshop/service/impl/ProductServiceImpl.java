package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.CategoryRepository;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.service.ProductService;
import com.nguyenpham.oganicshop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> findAll(List<Long> listId) {
        return productRepository.findAllById(listId);
    }

    @Override
    public List<ProductResponse> getProductRecommend(List<Long> listBookIds) {
        ProductConverter converter = new ProductConverter();
        List<ProductResponse> responses = new ArrayList<>();
        listBookIds.forEach(bookId -> {
            responses.add(converter.entityToDto(productRepository.findByBookId(bookId)));
        });
        return responses;
    }

    @Override
    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) throws IOException {
        Product product = new Product();
        Product lastProductInDb = productRepository.findTopByOrderByIdDesc();
        product = new ProductConverter().dtoToEntity(product, productRequest, lastProductInDb.getId());
        Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
        product.setCategory(category);
        product = productRepository.save(product);
        ProductResponse productResponse = new ProductConverter().entityToDtoNotReviews(product);
        if (productResponse != null) {
            return productResponse;
        }
        return null;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(ProductRequest request) throws IOException {
        Product product = productRepository.findById(request.getId()).get();
        product = new ProductConverter().dtoToEntity(product, request, product.getId());
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        product.setCategory(category);
        product = productRepository.save(product);
        ProductResponse productResponse = new ProductConverter().entityToDtoNotReviews(product);
        if (productResponse != null) {
            return productResponse;
        }
        return null;
    }

    @Override
    @Transactional
    public int importProduct(long productId, int amount) {
        Product product = productRepository.findById(productId).get();
        product.setAmount(product.getAmount() + amount);
        return productRepository.save(product).getAmount();
    }

    @Override
    @Transactional
    public boolean stopBusinessProduct(long productId) {
        Product product = productRepository.findById(productId).get();
        product.setStopBusiness(true);
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean openBusinessProduct(long productId) {
        Product product = productRepository.findById(productId).get();
        product.setStopBusiness(false);
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public long countNumberProduct() {
        return productRepository.count();
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        ProductConverter productConverter = new ProductConverter();
        return productRepository.findAll().stream().map(product -> productConverter.entityToDtoNotReviews(product)).collect(Collectors.toList());
    }

    @Override
    public Page<Product> getProductsByCategory(long categoryId, double minPrice, double maxPrice, int pageNum, int pageSize, String sortField, String sortDir )
    {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
            return productRepository.findProductsByCategoryCategoryAndFilterPrice(categoryId, minPrice, maxPrice, pageable);
        }
        return productRepository.findProductsByCategoryId(categoryId, pageable);
    }

    @Override
    public ProductResponse getProductById(long productid) {
        try {
            Product product = productRepository.findById(productid).orElse(null);
            ProductConverter converter = new ProductConverter();
            return converter.entityToDto(product);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ProductResponse getProductByUrl(String productUrl) {
        try {
            Product product = productRepository.findByProductUrl(productUrl);
            ProductConverter converter = new ProductConverter();
            return converter.entityToDto(product);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Product getProductDetail(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    public Page<Product> getProductsByKeyword(String keyword, double minPrice, double maxPrice, int pageNum, int pageSize, String sortField, String sortDir) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
            return productRepository.findProductsByKeywordAndPrice(keyword, minPrice, maxPrice, pageable);
        }
        return productRepository.findProductsByKeyword(keyword, pageable);
    }

    @Override
    public int getAmountAvailable(String productUrl) {
        return productRepository.findByProductUrl(productUrl).getAmount();
    }

    @Override
    public boolean isProvideEnoughQuantity(String productUrl, int quantity) {
        int quantityAvailable = productRepository.findByProductUrl(productUrl).getAmount();
        if (quantityAvailable >= quantity) {
            return true;
        }
        return false;
    }
}
