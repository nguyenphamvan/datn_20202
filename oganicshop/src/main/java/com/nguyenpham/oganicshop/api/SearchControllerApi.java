package com.nguyenpham.oganicshop.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.Supplier;
import com.nguyenpham.oganicshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SearchControllerApi {

    private ProductService productService;

    @Autowired
    public SearchControllerApi(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/search")
    public Object getViewSearch(@RequestBody ObjectNode object) {

        String search = "";
        String sort = "asc";
        String filed = "finalPrice";
        int pageNum = 1;
        int pageSize = 6;
        if (object.has("keyword")) {
            search = object.get("keyword").asText();
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

        Map<String, Object> result = new HashMap<>();
        Page<Product> page = productService.searchProductByKeyword(search, pageNum, pageSize, filed, sort);
        Set<Supplier> setSuppliers = new HashSet<>();
        Set<CategoryDto> setCategorySearch = new HashSet<>();
        for (Product p : page.getContent()) {
            setSuppliers.add(p.getSupplier());
            setCategorySearch.add(p.getCategory().convertToCategoryDto());
        }
        ProductConverter productConverter = new ProductConverter();
        List<ProductResponseDto> products = page.getContent().stream().map(product -> productConverter.entityToDtoNotReviews(product)).collect(Collectors.toList());
        result.put("suppliers", setSuppliers);
        result.put("categories", setCategorySearch);
        result.put("products", products);
        result.put("page", pageNum);
        result.put("totalPages", page.getTotalPages());
        result.put("pageSize", pageSize);
        result.put("sortBy", filed);
        result.put("sort", sort);
        result.put("keyword", search);

        return result;
    }
}
