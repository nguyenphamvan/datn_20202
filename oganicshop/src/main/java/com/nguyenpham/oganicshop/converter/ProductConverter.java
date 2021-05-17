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
        productResponse.setProductName(product.getName());
        productResponse.setProductUrl(product.getUrl());
        productResponse.setBaseDescription(product.getBaseDescription());
        productResponse.setDetailDescription(product.getDetailDescription().trim());
        productResponse.setCategoryName(product.getCategory().getCategoryName());
        productResponse.setSupplierName(product.getSupplier().getName());

        //image
        if (product.getImage() != null) {
            List<String> imagesDb = Arrays.asList(product.getImage().split(","))
                    .stream().map(img -> "/images/products/" + product.getId() + "/" + img).collect(Collectors.toList());
            productResponse.setMainImage(imagesDb.get(0));
            productResponse.setImages(imagesDb);
        } else {
            productResponse.setMainImage("/images/products/default.png");
            productResponse.setImages(null);
        }
        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setFinalPrice(product.getFinalPrice());

        productResponse.setRating(product.getRating());
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

        if (product.getReviews() != null) {
            ReviewConverter converter = new ReviewConverter();
            List<ReviewResponse> reviews = product.getReviews().stream().map(rv -> converter.entityToDto(rv)).collect(Collectors.toList());
            productResponse.setReviews(reviews);
            productResponse.setNumberOfReviews(product.getReviews().size());
        } else {
            productResponse.setReviews(null);
            productResponse.setNumberOfReviews(0);
        }

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
        product.setName(request.getName());
        product.setUrl(request.getName().replace(" ", "-"));
        ArrayList<String> images = new ArrayList<>();
        for (MultipartFile image : request.getImages()) {
            if (image.isEmpty()) {
                continue;
            }
            images.add(org.springframework.util.StringUtils.cleanPath(image.getOriginalFilename()));
        }
        if (images.size() > 0) {
            product.setImage(StringUtils.join(images, ","));
        }
        product.setBaseDescription(request.getBaseDescription());
        product.setDetailDescription(request.getDetailDescription().trim());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setFinalPrice(request.getPrice() - request.getDiscount());
        product.setAmount(request.getAmount());
        product.setRating(0);
        product.setReviews(null);

        return product;
    }

}
