package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();
    List<CategoryDto> getListCategory();
    Category getByCategoryUrl(String categoryUrl);
    Category addCategory(CategoryDto categoryRequest);
    Category updateCategory(Category category);
}
