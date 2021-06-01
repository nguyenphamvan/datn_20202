package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.CategoryConverter;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.BaseResponse;
import com.nguyenpham.oganicshop.dto.ProductRequest;
import com.nguyenpham.oganicshop.dto.ProductResponse;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.OrderService;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
public class ProductControllerApi {

    private OrderService orderService;
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public ProductControllerApi(OrderService orderService, ProductService productService, CategoryService categoryService) {
        this.orderService = orderService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // Admin
    @GetMapping("/api/admin/product/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/api/admin/product/view/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse getProductDetail(@PathVariable("productId") long productId) {
        ProductConverter converter = new ProductConverter();
        ProductResponse response = converter.entityToDto(productService.getProductDetail(productId));
        response.setReviews(null);
        response.setAmountSold(orderService.countNumberOfProductInOrder(productId));
        return response;
    }

    @PostMapping("/api/admin/product/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse addProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        return productService.addProduct(productRequest);
    }

    @PutMapping("/api/admin/product/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse editProduct(@ModelAttribute ProductRequest productRequest) throws IOException {
        return productService.updateProduct(productRequest);
    }

    @PutMapping("/api/admin/product/stopBusiness/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean stopBusiness(@PathVariable("productId") long productId) {
        return productService.stopBusinessProduct(productId);
    }

    @PutMapping("/api/admin/product/openBusiness/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean openBusiness(@PathVariable("productId") long productId) {
        return productService.openBusinessProduct(productId);
    }

    @PutMapping("/api/admin/product/import/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object importAmountProduct(@PathVariable("productId") long productId, @RequestBody ObjectNode objectNode) {
        int amount = objectNode.get("amount").asInt();
        int amountAvailable = productService.importProduct(productId, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("product", new ProductConverter().entityToDtoNotReviews(productService.getProductDetail(productId)));
        response.put("message", "Bạn vừa nhập thêm " + amount + ", số lượng hiện tại là : " + amountAvailable);
        return response;
    }

    // User
    @PostMapping("/api/products/collections")
    public Object getProductsByCategory(@RequestBody ObjectNode object) {
        Page<Product> page = null;
        String categoryUrl = "";
        String sort = "asc";
        String filed = "name";
        int pageNum = 1;
        int pageSize = 6;
        int minPrice = 0;
        int maxPrice = 0;
        if (object.has("category")) {
            categoryUrl = object.get("category").asText();
        }
        if (object.has("sort")) {
            sort = object.get("sort").asText();
        }
        if (object.has("sortBy")) {
            filed = object.get("sortBy").asText();
        }
        if (object.has("page")) {
            pageNum = object.get("page").asInt();
        }
        if (object.has("pageSize")) {
            pageSize = object.get("pageSize").asInt();
        }
        if (object.has("minPrice")) {
            minPrice = object.get("minPrice").asInt();
        }
        if (object.has("maxPrice")) {
            maxPrice = object.get("maxPrice").asInt();
        }

        Map<String, Object> result = new HashMap<>();
        if (!categoryUrl.equals("")) {
            Category category = categoryService.getByCategoryUrl(categoryUrl);
            page = productService.getProductsByCategory(categoryUrl, minPrice, maxPrice, pageNum, pageSize, filed, sort);
            result.put("categories", Arrays.asList(new CategoryConverter().entityToDto(category)));
        }

        ProductConverter productConverter = new ProductConverter();
        List<ProductResponse> products = page.getContent().stream().map(product -> productConverter.entityToDtoNotReviews(product)).collect(Collectors.toList());

        BaseResponse br = new BaseResponse();
        if (products.size() > 0) {
            result.put("products", products);
            result.put("page", pageNum);
            result.put("totalPages", page.getTotalPages());
            result.put("pageSize", pageSize);
            result.put("sortBy", filed);
            result.put("sort", sort);
            result.put("minPrice", minPrice);
            result.put("maxPrice", maxPrice);

            br.setData(result);
            br.setStatus(true);
        } else {
            br.setData(result);
            br.setErrMessage("Không tìm thấy sản phẩm nào");
            br.setStatus(false);
        }
        return br;
    }

    @GetMapping("/api/products/{productUrl}")
    public ResponseEntity<?> getProductByUrl(@PathVariable("productUrl") String productUrl) {
        BaseResponse br = new BaseResponse();
        Map<String, Object> response = new HashMap<>();
        ProductResponse product = productService.getProductByUrl(productUrl);
        response.put("product", product);
        if (product != null) {
            br.setData(response);
            br.setStatus(true);
        } else {
            br.setErrMessage("Sản phẩm không tồn tạio");
            br.setStatus(false);
        }
        return new ResponseEntity<Object>(br, HttpStatus.OK);
    }

    @GetMapping("/api/products/quantity-available/{productUrl}")
    public int getQuantityAvailable(@PathVariable("productUrl") String productUrl) {
        return productService.getAmountAvailable(productUrl);
    }

    @GetMapping("/api/products/check-provide-enough-quantity")
    public boolean isProvideEnoughQuantity(@RequestParam("productUrl") String productUrl, @RequestParam("quantity") int quantity) {
        return productService.isProvideEnoughQuantity(productUrl, quantity);
    }
}
