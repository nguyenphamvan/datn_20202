package com.nguyenpham.oganicshop.api.admin;

import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.ProductRequestDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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

    @GetMapping("/view/{productId}")
    public ProductResponseDto getProductDetail(@PathVariable("productId") long productId) {
        ProductConverter converter = new ProductConverter();
        return converter.entityToDto(productService.getProductById(productId));
    }

    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.insertProduct(productRequestDto);
    }

    @PutMapping("/edit")
    public ProductResponseDto editProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.editProduct(productRequestDto);
    }

    @PutMapping("/stopBusiness/{productId}")
    public boolean stopBusiness(@PathVariable("productId") long productId) {
        return productService.stopBusinessProduct(productId);
    }
}
