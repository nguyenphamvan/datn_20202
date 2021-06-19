package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;

public class CategoryConverter implements GeneralConverter<Category, CategoryDto, CategoryDto>{

    @Override
    public CategoryDto entityToDto(Category category) {
        CategoryDto response = new CategoryDto();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        return response;
    }

    @Override
    public Category dtoToEntity(CategoryDto d) {
        return null;
    }
}
