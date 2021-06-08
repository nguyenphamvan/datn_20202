package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter implements GeneralConverter<Product, ProductRequest, ProductResponse>{

    @Override
    public ProductResponse entityToDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setProductName(product.getTitle());
        productResponse.setProductUrl(product.getProductUrl());
        productResponse.setBaseDescription(product.getDescription());
        productResponse.setCategoryName(product.getCategory().getCategoryName());

        //image
        List<String> images = new ArrayList<>();
        if (product.getMainImage() != null) {
            images.add(product.getMainImage());
            productResponse.setMainImage(product.getMainImage());
        } else {
            productResponse.setMainImage("/images/products/default.png");
            productResponse.setImages(null);
        }

        if (product.getSmallImage() != null) {
            images.add(product.getSmallImage());
        }
        productResponse.setImages(images);

        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setFinalPrice(product.getFinalPrice());

        productResponse.setRating(product.getAverageRating());
        productResponse.setAmount(product.getAmount());
        if (product.isStopBusiness()) {
            productResponse.setStatus("Ngừng bán");
        } else if ((product.getAmount() > 0 ) && (product.getAmount() < 10)) {
            productResponse.setStatus("Sắp hết hàng");
        } else if (product.getAmount() == 0 ) {
            productResponse.setStatus("Đã hết hàng");
        } else {
            productResponse.setStatus("Đang bán");
        }

//        if (product.getReviews() != null) {
//            ReviewConverter converter = new ReviewConverter();
//            List<ReviewResponse> reviews = product.getReviews().stream().map(rv -> converter.entityToDto(rv)).collect(Collectors.toList());
//            productResponse.setReviews(reviews);
//            productResponse.setNumberOfReviews(product.getReviews().size());
//        } else {
//            productResponse.setReviews(null);
//            productResponse.setNumberOfReviews(0);
//        }

        return productResponse;
    }

    public ProductResponse entityToDtoNotReviews(Product product) {
        ProductResponse productResponse = entityToDto(product);
        productResponse.setReviews(null);
        return productResponse;
    }

    @Override
    public Product dtoToEntity(ProductRequest request) {
        Product product = new Product();
        product.setId(request.getId());
        product.setTitle(request.getName());
        ArrayList<String> images = new ArrayList<>();
        MultipartFile image = request.getImages();
        product.setMainImage(org.springframework.util.StringUtils.cleanPath(image.getOriginalFilename()));
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setFinalPrice(request.getPrice() - request.getDiscount()*request.getPrice());
        product.setAmount(request.getAmount());
        product.setAverageRating(0);
        product.setReviews(null);

        return product;
    }

}
