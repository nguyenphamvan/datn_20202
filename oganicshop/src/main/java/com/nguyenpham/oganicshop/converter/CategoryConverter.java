package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;

public class CategoryConverter implements GeneralConverter<Category, CategoryDto, CategoryDto>{

    @Override
    public CategoryDto entityToDto(Category category) {
        CategoryDto response = new CategoryDto();
        response.setId(category.getId());
        response.setCategoryName(category.getCategoryName());
        response.setCategoryUrl(category.getCategoryUrl());
        if (category.getSubCategories() != null) {
            for(Category c : category.getSubCategories()) {
                CategoryDto subCategory = new CategoryDto();
                subCategory.setId(c.getId());
                subCategory.setCategoryName(c.getCategoryName());
                subCategory.setCategoryUrl(c.getCategoryUrl());
                subCategory.setParentId(c.getParent().getId());
                response.addSubCategory(subCategory);
            }
        }
        return response;
    }

    @Override
    public Category dtoToEntity(CategoryDto d) {
        return null;
    }
}
