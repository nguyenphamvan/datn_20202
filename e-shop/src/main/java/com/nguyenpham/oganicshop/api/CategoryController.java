package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getListCategory() {
        return new ResponseEntity<Object>(categoryService.getListCategory(), HttpStatus.OK);
    }

    @GetMapping("/categories/category")
    public ResponseEntity<?> getCategoryByCategoryById(@RequestParam("id") long categoryId) {
        return new ResponseEntity<Object>(categoryService.getByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") long categoryId) {
        return new ResponseEntity<Object>(categoryService.getByCategory(categoryId), HttpStatus.OK);
    }

//    @PostMapping("/categories/insert")
//    public ResponseEntity<?> insertCategory(@RequestBody CategoryDto categoryRequest) {
//        Category savedCategory = categoryService.addCategory(categoryRequest);
//        return new ResponseEntity<Object>(savedCategory, HttpStatus.OK);
//    }


}
