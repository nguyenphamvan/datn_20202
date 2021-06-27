package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.CategoryResponse;
import com.nguyenpham.oganicshop.entity.Category;

public class CategoryConverter implements GeneralConverter<Category, CategoryResponse, CategoryResponse>{

    @Override
    public CategoryResponse entityToDto(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        return response;
    }

    @Override
    public Category dtoToEntity(CategoryResponse d) {
        return null;
    }
}
