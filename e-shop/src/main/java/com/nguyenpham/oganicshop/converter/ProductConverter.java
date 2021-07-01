package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.constant.Constant;
import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.dto.ReviewResponse;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.CategoryRepository;
import com.nguyenpham.oganicshop.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter implements GeneralConverter<Product, ProductRequest, ProductResponse>{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductResponse entityToDto(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setProductName(product.getTitle());
        productResponse.setOriginalTitle(product.getOriginalTitle());
        productResponse.setProductUrl(product.getProductUrl());
        productResponse.setAuthor(product.getAuthors());
        productResponse.setSince(product.getOriginalPublicationYear());
        productResponse.setDescription(product.getDescription());
        productResponse.setCategoryName(product.getCategory().getCategoryName());
        //image
        if (product.getMainImage() != null) {
            productResponse.setMainImage(product.getMainImage());
        } else {
            productResponse.setMainImage("/images/products/default.png");
        }

        if (product.getSmallImage() != null) {
            productResponse.setSmallImage(product.getSmallImage());
        } else {
            productResponse.setSmallImage("/images/products/default.png");
        }

        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setFinalPrice(product.getFinalPrice());
        productResponse.setNumberOfReviews(product.getRatingsCount());
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

        return productResponse;
    }

    public ProductResponse entityToDtoNotReviews(Product product) {
        ProductResponse productResponse = entityToDto(product);
        productResponse.setReviews(null);
        return productResponse;
    }

    public Product dtoToEntity(Product product, ProductRequest request, long lastId) throws IOException {
        product.setId(request.getId());
        product.setTitle(request.getName());
        String[] proUrl = product.getTitle().replace("/","").replace("\"", "").replace("#", "")
                    .replace("*","").trim().toLowerCase().split(" ");
        String newUrl = "";
        if (product.getId() > 0) {
            newUrl = String.join("_", proUrl) + "_" + lastId;
        } else {
            newUrl = String.join("_", proUrl) + "_" + (lastId + 1);
        }
        product.setProductUrl(newUrl);
        product.setAuthors(request.getAuthors());
        if (!request.getImage1().getOriginalFilename().equals("")) {
            if (product.getId() > 0) {
                product.setMainImage(setImageForProduct(request.getImage1(), lastId));
            } else {
                product.setMainImage(setImageForProduct(request.getImage1(), lastId + 1));
            }

        }
        if (!request.getImage2().getOriginalFilename().equals("")) {
            if (product.getId() > 0) {
                product.setSmallImage(setImageForProduct(request.getImage2(), lastId));
            } else {
                product.setSmallImage(setImageForProduct(request.getImage2(), lastId + 1));
            }
        }
        product.setDescription(request.getDescription());
        product.setOriginalPublicationYear(request.getPublishYear());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount()/100);
        product.setFinalPrice(request.getPrice() - (request.getDiscount()/100)*request.getPrice());
        return product;
    }

    @Override
    public Product dtoToEntity(ProductRequest request) throws IOException {
        return null;
    }

    public String setImageForProduct(MultipartFile image, long idProduct) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = Constant.DIR_UPLOAD_IMAGE_PRODUCT + idProduct;
        FileUploadUtil.saveFile(uploadDir, fileName, image);
        return "/images/products/" + idProduct + "/" + fileName;
    }

}
