package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Supplier;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.ProductService;
import com.nguyenpham.oganicshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private SupplierService supplierService;

    @Autowired
    public ProductControllerApi(ProductService productService, CategoryService categoryService, SupplierService supplierService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    @GetMapping("/{productUrl}")
    public ResponseEntity<?> getListProductByCategoryUrl(@PathVariable("productUrl") String productUrl) {
        Map<String, Object> response = new HashMap<>();
        ProductResponseDto product = productService.getProduct(productUrl);
        List<CategoryDto> categories = categoryService.getListCategory();
        Collections.sort(categories);
        response.put("product", product);
        response.put("categories", categories);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("/collections")
    public Object getListProductByCategoryUrl(@RequestBody ObjectNode object) {
        Page<Product> page = null;
        String categoryUrl = "";
        String supplierName = "";
        String sort = "asc";
        String filed = "name";
        int pageNum = 1;
        int pageSize = 6;
        int minPrice = 0;
        int maxPrice = 0;
        if (object.has("category")) {
            categoryUrl = object.get("category").asText();
        }
        if (object.has("supplier")) {
            supplierName = object.get("supplier").asText();
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
        if (categoryUrl.equals("") && !supplierName.equals("")) {
            Supplier supplier = supplierService.findSupplierByName(supplierName);
            page = productService.getProductsBySupplier(supplierName, minPrice, maxPrice, pageNum, pageSize, filed, sort);
            Set<Category> setCategory = new HashSet<>();
            for (Product p : page.getContent()) {
                setCategory.add(p.getCategory());
            }
            result.put("categories", setCategory.stream().map(category -> category.convertToCategoryDto()).collect(Collectors.toSet()));
            result.put("suppliers", Arrays.asList(supplier));
        } else if (!categoryUrl.equals("") && supplierName.equals("")) {
            Category category = categoryService.getByCategoryUrl(categoryUrl);
            page = productService.getProductsByCategory(categoryUrl, minPrice, maxPrice, pageNum, pageSize, filed, sort);
            List<Supplier> suppliers = supplierService.findSuppliersByCategory(categoryUrl);
            result.put("suppliers", suppliers);
            result.put("categories", Arrays.asList(category.convertToCategoryDto()));

        } else {
            page = productService.getProductsByCategoryAndSupplier(categoryUrl, supplierName, minPrice, maxPrice, pageNum, pageSize, filed, sort);
            Supplier supplier = supplierService.findSupplierByName(supplierName);
            Category category = categoryService.getByCategoryUrl(categoryUrl);

            result.put("suppliers", Arrays.asList(supplier));
            result.put("categories", Arrays.asList(category.convertToCategoryDto()));

        }

        List<ProductResponseDto> products = page.getContent().stream().map(product -> product.convertToDtoNotIncludeReviews()).collect(Collectors.toList());

        result.put("products", products);
        result.put("page", pageNum);
        result.put("totalPages", page.getTotalPages());
        result.put("pageSize", pageSize);
        result.put("sortBy", filed);
        result.put("sort", sort);
        result.put("minPrice", minPrice);
        result.put("maxPrice", maxPrice);
        return result;
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
