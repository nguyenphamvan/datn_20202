package com.nguyenpham.oganicshop.api.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.ProductRequestDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin/product")
public class ManagerProductApi {

    private OrderService orderService;
    private ProductService productService;

    @Autowired
    public ManagerProductApi(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<ProductResponseDto> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/view/{productId}")
    public ProductResponseDto getProductDetail(@PathVariable("productId") long productId) {
        ProductConverter converter = new ProductConverter();
        ProductResponseDto response = converter.entityToDto(productService.getProductById(productId));
        response.setReviews(null);
        response.setAmountSold(orderService.countNumberOfProductInOrder(productId));
        return response;
    }

    @PostMapping("/add")
    public ProductResponseDto addProduct(@ModelAttribute ProductRequestDto productRequestDto) throws IOException {
        return productService.insertProduct(productRequestDto);
    }

    @PutMapping("/edit")
    public ProductResponseDto editProduct(@ModelAttribute ProductRequestDto productRequestDto) throws IOException {
        return productService.editProduct(productRequestDto);
    }

    @PutMapping("/stopBusiness/{productId}")
    public boolean stopBusiness(@PathVariable("productId") long productId) {
        return productService.stopBusinessProduct(productId);
    }

    @PutMapping("/import/{productId}")
    public Object importAmountProduct(@PathVariable("productId") long productId, @RequestBody ObjectNode objectNode) {
        int amount = objectNode.get("amount").asInt();
        int amountAvailable = productService.importProduct(productId, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("amountAfterImport", amountAvailable);
        response.put("message", "Bạn vừa nhập thêm " + amount + ", số lượng hiện tại là : " + amountAvailable);
        return response;
    }
}
