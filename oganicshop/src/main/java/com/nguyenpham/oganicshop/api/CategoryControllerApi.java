package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryControllerApi {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getListCategory() {
        return new ResponseEntity<Object>(categoryService.getListCategory(), HttpStatus.OK);
    }

    @GetMapping("/{categoryURL}")
    public ResponseEntity<?> getCategoryByCategoryUrl(@PathVariable("categoryURL") String categoryUrl) {
        return new ResponseEntity<Object>(categoryService.getByCategoryUrl(categoryUrl), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insertCategory(@RequestBody CategoryDto categoryRequest) {
        Category savedCategory = categoryService.addCategory(categoryRequest);
        return new ResponseEntity<Object>(savedCategory, HttpStatus.OK);
    }


}
