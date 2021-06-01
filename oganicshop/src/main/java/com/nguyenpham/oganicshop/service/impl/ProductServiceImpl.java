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
    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest) throws IOException {
        Product product = new ProductConverter().dtoToEntity(productRequest);
        Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
        product.setCategory(category);
//        product.setSupplier(supplier);
        product = productRepository.save(product);
        for (MultipartFile image : productRequest.getImages()) {
            if (image.isEmpty()) {
                continue;
            }
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            String uploadDir = Constant.DIR_UPLOAD_IMAGE_PRODUCT + product.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, image);
        }
        ProductResponse productResponse = new ProductConverter().entityToDtoNotReviews(product);
        if (productResponse != null) {
            return productResponse;
        }
        return null;
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(ProductRequest productRequest) throws IOException {
        ProductResponse productResponse = null;
        Product product = productRepository.findById(productRequest.getId()).get();
        product.setName(productRequest.getName());
        product.setUrl(productRequest.getName().replace(" ", "-"));
        product.setBaseDescription(productRequest.getBaseDescription());
        product.setDetailDescription(productRequest.getDetailDescription());
        product.setPrice(productRequest.getPrice());
        product.setDiscount(productRequest.getDiscount());
        product.setFinalPrice(productRequest.getPrice() - productRequest.getDiscount());
        Category category = categoryRepository.findById(productRequest.getCategoryId()).get();
        product.setCategory(category);
//        product.setSupplier(supplier);
        ArrayList<String> images = new ArrayList<>();
        for (MultipartFile image : productRequest.getImages()) {
            if (image.isEmpty()) {
                continue;
            }
            images.add(StringUtils.cleanPath(image.getOriginalFilename()));
        }
        if (images.size() > 0) {
            product.setImage(org.apache.commons.lang3.StringUtils.join(images, "-"));
            for (MultipartFile image : productRequest.getImages()) {
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
    public long countNumberProduct() {
        return productRepository.count();
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        ProductConverter productConverter = new ProductConverter();
        return productRepository.findAll().stream().map(product -> productConverter.entityToDtoNotReviews(product)).collect(Collectors.toList());
    }

    @Override
    public Page<Product> getProductsByCategory(String categoryUrl, int minPrice, int maxPrice, int pageNum, int pageSize, String sortField, String sortDir )
    {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        if (((minPrice > 0) || (maxPrice > 0)) && (minPrice < maxPrice)) {
            return productRepository.findProductsByCategoryCategoryUrlAndFilterPrice(categoryUrl, minPrice, maxPrice, pageable);
        }
        return productRepository.findProductsByCategoryCategoryUrl(categoryUrl, pageable);
    }

    @Override
    public ProductResponse getProductByUrl(String productUrl) {
        try {
            Product product = productRepository.findByUrl(productUrl).orElse(null);
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
    public Page<Product> getProductsByKeyword(String keyword, int pageNum, int pageSize, String sortField, String sortDir) {
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
