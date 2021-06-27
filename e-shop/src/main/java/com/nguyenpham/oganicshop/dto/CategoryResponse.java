package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CategoryResponse implements Comparable<CategoryResponse>{
    private Long id;
    private String categoryName;

    @Override
    public int compareTo(CategoryResponse o) {
        return this.getId().compareTo(o.getId());
    }
}
