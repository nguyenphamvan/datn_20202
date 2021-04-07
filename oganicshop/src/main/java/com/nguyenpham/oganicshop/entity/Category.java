package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.nguyenpham.oganicshop.dto.CategoryDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Builder
@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer"})
@EqualsAndHashCode(exclude= {"parent", "subCategories"})
@ToString(exclude= {"parent", "subCategories"})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String categoryUrl;
    @Column(nullable = false)
    private String categoryName;
    @JsonIgnore
    @CreationTimestamp
    private Date createdAt;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Category parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Category> subCategories;

    public Category(String categoryUrl, String categoryName) {
        this.categoryUrl = categoryUrl;
        this.categoryName = categoryName;
    }

    public CategoryDto convertToCategoryDto() {
        CategoryDto category = new CategoryDto();
        category.setId(this.getId());
        category.setCategoryName(this.getCategoryName());
        category.setCategoryUrl(this.getCategoryUrl());
        if (this.getSubCategories() != null) {
            for(Category c : this.getSubCategories()) {
                CategoryDto subCategory = new CategoryDto();
                subCategory.setId(c.getId());
                subCategory.setCategoryName(c.getCategoryName());
                subCategory.setCategoryUrl(c.getCategoryUrl());
                subCategory.setParentId(c.getParent().getId());
                category.addSubCategory(subCategory);
            }
        }
        return category;
    }


}
