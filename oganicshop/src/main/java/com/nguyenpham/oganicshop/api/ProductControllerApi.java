package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products/")
public class ProductControllerApi {

    private ProductService productService;

    @Autowired
    public ProductControllerApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productUrl}")
    public ResponseEntity<?> getListProductByCategoryUrl(@PathVariable("productUrl") String productUrl) {
        return new ResponseEntity<Product>(productService.getProduct(productUrl), HttpStatus.OK);
    }

    @GetMapping("/quantity-available/{productUrl}")
    public int getQuantityAvailable(@PathVariable("productUrl") String productUrl) {
        return productService.getAmountAvailable(productUrl);
    }

    @GetMapping("/check-provide-enough-quantity/{productUrl}")
    public boolean isProvideEnoughQuantity(@PathVariable("productUrl") String productUrl, @RequestParam("quantity") int quantity) {
        return productService.isProvideEnoughQuantity(productUrl, quantity);
    }
}
