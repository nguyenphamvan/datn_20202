package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.ProductRequestDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter implements GeneralConverter<Product, ProductRequestDto, ProductResponseDto>{

    @Override
    public ProductResponseDto entityToDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setProductName(product.getName());
        productResponseDto.setProductUrl(product.getUrl());
        productResponseDto.setBaseDescription(product.getBaseDescription());
        productResponseDto.setDetailDescription(product.getDetailDescription());
        productResponseDto.setCategoryName(product.getCategory().getCategoryName());
        productResponseDto.setSupplierName(product.getSupplier().getName());

        //image
        if (product.getImage() != null) {
            String[] imagesDb = product.getImage().split(",");
            productResponseDto.setMainImage("/images/products/" + product.getId() + "/" + imagesDb[0]);
            productResponseDto.setImages(imagesDb);
        } else {
            productResponseDto.setMainImage("/images/products/default.png");
            productResponseDto.setImages(null);
        }
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setDiscount(product.getDiscount());
        productResponseDto.setFinalPrice(product.getFinalPrice());

        productResponseDto.setRating(product.getRating());
        productResponseDto.setAmount(product.getAmount());
        if (product.isStopBusiness()) {
            productResponseDto.setStatus("Ngừng bán");
        } else if ((product.getAmount() > 0 ) && (product.getAmount() < 10)) {
            productResponseDto.setStatus("Sắp hết hàng");
        } else if (product.getAmount() == 0 ) {
            productResponseDto.setStatus("Đã hết hàng");
        } else {
            productResponseDto.setStatus("Đang bán");
        }

        if (product.getReviews() != null) {
            ReviewConverter converter = new ReviewConverter();
            List<ResponseReviewDto> reviews = product.getReviews().stream().map(rv -> converter.entityToDto(rv)).collect(Collectors.toList());
            productResponseDto.setReviews(reviews);
            productResponseDto.setNumberOfReviews(product.getReviews().size());
        } else {
            productResponseDto.setReviews(null);
            productResponseDto.setNumberOfReviews(0);
        }

        return productResponseDto;
    }

    public ProductResponseDto entityToDtoNotReviews(Product product) {
        ProductResponseDto productResponseDto = entityToDto(product);
        productResponseDto.setReviews(null);
        return productResponseDto;
    }

    @Override
    public Product dtoToEntity(ProductRequestDto request) {
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
        product.setDetailDescription(request.getDetailDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setFinalPrice(request.getPrice() - request.getDiscount());
        product.setAmount(request.getAmount());
        product.setRating(0);
        product.setReviews(null);

        return product;
    }

}
