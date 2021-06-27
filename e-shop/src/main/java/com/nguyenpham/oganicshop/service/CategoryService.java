package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.CategoryResponse;
import com.nguyenpham.oganicshop.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories();
    List<CategoryResponse> getListCategory();
    CategoryResponse getByCategoryId(long categoryId);
    Category getByCategory(long categoryId);
//    Category addCategory(CategoryDto categoryRequest);
//    Category updateCategory(Category category);
}
