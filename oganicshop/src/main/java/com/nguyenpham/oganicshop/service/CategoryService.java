package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.CategoryRequest;
import com.nguyenpham.oganicshop.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getListCategory();
    Category getByCategoryUrl(String categoryUrl);
    Category addCategory(CategoryRequest categoryRequest);
    Category updateCategory(Category category);
}
