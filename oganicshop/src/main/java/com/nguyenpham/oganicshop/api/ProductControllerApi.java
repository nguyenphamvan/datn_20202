package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.dto.ProductDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.ProductService;
import com.nguyenpham.oganicshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products/")
public class ProductControllerApi {

    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductControllerApi(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{productUrl}")
    public ResponseEntity<?> getListProductByCategoryUrl(@PathVariable("productUrl") String productUrl) {
        Map<String, Object> response = new HashMap<>();
        ProductDto product = productService.getProduct(productUrl);
        List<CategoryDto> categories = categoryService.getListCategory();
        Collections.sort(categories);
        response.put("product", product);
        response.put("categories", categories);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/quantity-available/{productUrl}")
    public int getQuantityAvailable(@PathVariable("productUrl") String productUrl) {
        return productService.getAmountAvailable(productUrl);
    }

    @GetMapping("/check-provide-enough-quantity")
    public boolean isProvideEnoughQuantity(@RequestParam("productUrl") String productUrl, @RequestParam("quantity") int quantity) {
        return productService.isProvideEnoughQuantity(productUrl, quantity);
    }

    @GetMapping("/api/{productUrl}")
    public ResponseEntity<?> getProductDetail(@PathVariable("productUrl") String productUrl) {
        return null;
    }
}
