package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.converter.ProductConverter;
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
import com.nguyenpham.oganicshop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @Transactional
    public ProductResponseDto insertProduct(ProductRequestDto productRequestDto) throws IOException {
        Product product = new ProductConverter().dtoToEntity(productRequestDto);
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).get();
        Supplier supplier = supplierRepository.findById(productRequestDto.getSupplierId()).get();
        product.setCategory(category);
        product.setSupplier(supplier);
        product = productRepository.save(product);
        for (MultipartFile image : productRequestDto.getImages()) {
            if (image.isEmpty()) {
                continue;
            }
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            String uploadDir = Constant.DIR_UPLOAD_IMAGE_PRODUCT + product.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, image);
        }
        ProductResponseDto productResponse = new ProductConverter().entityToDtoNotReviews(product);
        if (productResponse != null) {
            return productResponse;
        }
        return null;
    }

    @Override
    @Transactional
    public ProductResponseDto editProduct(ProductRequestDto productRequestDto) throws IOException {
        ProductResponseDto productResponse = null;
        Product product = productRepository.findById(productRequestDto.getId()).get();
        product.setName(productRequestDto.getName());
        product.setUrl(productRequestDto.getName().replace(" ", "-"));
        product.setBaseDescription(productRequestDto.getBaseDescription());
        product.setDetailDescription(productRequestDto.getDetailDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setDiscount(productRequestDto.getDiscount());
        product.setFinalPrice(productRequestDto.getPrice() - productRequestDto.getDiscount());
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).get();
        Supplier supplier = supplierRepository.findById(productRequestDto.getSupplierId()).get();
        product.setCategory(category);
        product.setSupplier(supplier);
        ArrayList<String> images = new ArrayList<>();
        for (MultipartFile image : productRequestDto.getImages()) {
            if (image.isEmpty()) {
                continue;
            }
            images.add(StringUtils.cleanPath(image.getOriginalFilename()));
        }
        if (images.size() > 0) {
            product.setImage(org.apache.commons.lang3.StringUtils.join(images, "-"));
            for (MultipartFile image : productRequestDto.getImages()) {
                if (image.isEmpty()) {
                    continue;
                }
                String fileName = StringUtils.cleanPath(image.getOriginalFilename());
                String uploadDir = Constant.DIR_UPLOAD_IMAGE_PRODUCT + product.getId();
                FileUploadUtil.saveFile(uploadDir, fileName, image);
            }
        }
        productResponse = new ProductConverter().entityToDtoNotReviews(productRepository.save(product));
        return productResponse;
    }

    @Override
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
    public long countNumberProduct() {
        return productRepository.count();
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        ProductConverter productConverter = new ProductConverter();
        return productRepository.findAll().stream().map(product -> productConverter.entityToDtoNotReviews(product)).collect(Collectors.toList());
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
        ProductConverter converter = new ProductConverter();
        return converter.entityToDto(product);
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
