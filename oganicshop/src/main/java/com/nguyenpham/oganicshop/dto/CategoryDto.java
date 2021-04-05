package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryDto implements Comparable<CategoryDto>{
    private Long id;
    private String categoryUrl;
    private String categoryName;
    private Long parentId;
    private Set<CategoryDto> subCategory;
    public void addSubCategory(CategoryDto category) {
        if (subCategory == null) {
            subCategory = new HashSet<>();
        }
        subCategory.add(category);
    }

    @Override
    public int compareTo(CategoryDto o) {
        return this.getId().compareTo(o.getId());
    }
}
