package com.nguyenpham.oganicshop.api.admin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
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
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/view/{productId}")
    public ProductResponse getProductDetail(@PathVariable("productId") long productId) {
        ProductConverter converter = new ProductConverter();
        ProductResponse response = converter.entityToDto(productService.getProductDetail(productId));
        response.setReviews(null);
        response.setAmountSold(orderService.countNumberOfProductInOrder(productId));
        return response;
    }

    @PostMapping("/add")
    public ProductResponse addProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        return productService.addProduct(productRequest);
    }

    @PutMapping("/edit")
    public ProductResponse editProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        return productService.updateProduct(productRequest);
    }

    @PutMapping("/stopBusiness/{productId}")
    public boolean stopBusiness(@PathVariable("productId") long productId) {
        return productService.stopBusinessProduct(productId);
    }

    @PutMapping("/openBusiness/{productId}")
    public boolean openBusiness(@PathVariable("productId") long productId) {
        return productService.openBusinessProduct(productId);
    }

    @PutMapping("/import/{productId}")
    public Object importAmountProduct(@PathVariable("productId") long productId, @RequestBody ObjectNode objectNode) {
        int amount = objectNode.get("amount").asInt();
        int amountAvailable = productService.importProduct(productId, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("product", new ProductConverter().entityToDtoNotReviews(productService.getProductDetail(productId)));
        response.put("message", "Bạn vừa nhập thêm " + amount + ", số lượng hiện tại là : " + amountAvailable);
        return response;
    }
}
