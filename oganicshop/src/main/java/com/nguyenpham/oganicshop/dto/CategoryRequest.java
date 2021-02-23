package com.nguyenpham.oganicshop.dto;

import com.nguyenpham.oganicshop.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private String categoryUrl;
    private String categoryName;
    private Long parentId;
}
