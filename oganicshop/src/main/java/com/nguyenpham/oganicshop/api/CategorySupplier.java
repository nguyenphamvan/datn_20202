package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.service.CategoryService;
import com.nguyenpham.oganicshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategorySupplier {

    private CategoryService categoryService;
    private SupplierService supplierService;

    @Autowired
    public CategorySupplier(CategoryService categoryService, SupplierService supplierService) {
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    @GetMapping("/categories-suppliers")
    public ResponseEntity<?> getListCategoryAndSupplier() {
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryService.getListCategory());
        response.put("suppliers", supplierService.getAll());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getListCategory() {
        return new ResponseEntity<Object>(categoryService.getListCategory(), HttpStatus.OK);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<?> getListSupplier() {
        return new ResponseEntity<Object>(supplierService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/categories/category")
    public ResponseEntity<?> getCategoryByCategoryById(@RequestParam("id") long categoryId) {
        return new ResponseEntity<Object>(categoryService.getByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryURL}")
    public ResponseEntity<?> getCategoryByCategoryUrl(@PathVariable("categoryURL") String categoryUrl) {
        return new ResponseEntity<Object>(categoryService.getByCategoryUrl(categoryUrl), HttpStatus.OK);
    }

    @PostMapping("/categories/insert")
    public ResponseEntity<?> insertCategory(@RequestBody CategoryDto categoryRequest) {
        Category savedCategory = categoryService.addCategory(categoryRequest);
        return new ResponseEntity<Object>(savedCategory, HttpStatus.OK);
    }

    @PostMapping("/suppliers/insert")
    public ResponseEntity<?> insertSupplier(@RequestBody Supplier supplier) {
        return new ResponseEntity<Object>(supplierService.insertSupplier(supplier), HttpStatus.OK);
    }


}
