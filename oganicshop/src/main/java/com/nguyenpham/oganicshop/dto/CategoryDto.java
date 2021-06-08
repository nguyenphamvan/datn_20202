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
    private String categoryName;

    @Override
    public int compareTo(CategoryDto o) {
        return this.getId().compareTo(o.getId());
    }
}
