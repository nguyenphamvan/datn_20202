package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.converter.CategoryConverter;
import com.nguyenpham.oganicshop.dto.CategoryDto;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.repository.CategoryRepository;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "listCategory")
    public List<CategoryDto> getListCategory() {
        CategoryConverter converter = new CategoryConverter();
        List<CategoryDto> categories = categoryRepository.findAll().stream()
                .map(c -> converter.entityToDto(c)).collect(Collectors.toList());
        return categories;
    }

    @Override
    public CategoryDto getByCategoryId(long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return new CategoryConverter().entityToDto(category.get());
        }
        return null;
    }

    @Override
    public Category getByCategory(long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

//    @Override
//    public Category addCategory(CategoryDto categoryRequest) {
//        Category newCategory = new Category(categoryRequest.getCategoryUrl(), categoryRequest.getCategoryName());
//        if (categoryRequest.getParentId() != null) {
//            Category parentCategory = categoryRepository.findById(categoryRequest.getParentId()).orElse(null);
//            if(parentCategory != null) {
//                newCategory.setParent(parentCategory);
//            }
//        }
//        return categoryRepository.save(newCategory);
//    }

//    @Override
//    public Category updateCategory(Category category) {
//        return null;
//    }

}