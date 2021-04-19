package com.nguyenpham.oganicshop.api.admin;

import com.nguyenpham.oganicshop.dto.ProductRequestDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/product")
public class ManagerProductApi {

    private ProductService productService;

    @Autowired
    public ManagerProductApi(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductResponseDto> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProductDetail(@PathVariable("productId") long productId) {
        return productService.getProductById(productId).convertToDto();
    }

    @PostMapping("/insert")
    public ProductResponseDto insertProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.insertProduct(productRequestDto);
    }
}
