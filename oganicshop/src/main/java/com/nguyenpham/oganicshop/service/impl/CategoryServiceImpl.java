package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.CategoryRequest;
import com.nguyenpham.oganicshop.entity.Category;
import com.nguyenpham.oganicshop.repository.CategoryRepository;
import com.nguyenpham.oganicshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Cacheable(cacheNames = "listCategory")
    public List<Category> getListCategory() {
        return categoryRepository.findAllByParentIsNull();
    }

    @Override
    public Category getByCategoryUrl(String categoryUrl) {
        return categoryRepository.findByCategoryUrl(categoryUrl).orElse(null);
    }

    @Override
    public Category addCategory(CategoryRequest categoryRequest) {
        Category parentCategory = categoryRepository.findById(categoryRequest.getParentId()).orElse(null);
        Category newCategory = new Category(categoryRequest.getCategoryUrl(), categoryRequest.getCategoryName());
        if(parentCategory != null) {
            newCategory.setParent(parentCategory);
        }
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

}
