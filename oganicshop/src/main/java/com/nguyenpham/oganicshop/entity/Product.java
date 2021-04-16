package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.dto.ResponseReviewDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude={"category", "supplier"})
@ToString(exclude = {"category", "supplier"})

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String url;
    private String image;
    private String size;
    private String color;
    private String baseDescription;
    private String detailDescription;
    @Column(nullable = false)
    private int price;
    private int discount;
    @Column(nullable = false)
    private int finalPrice;
    private int rating;
    private int amount;

    @JsonIgnore
    @CreationTimestamp
    private Date createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Review> reviews;

    public ProductResponseDto convertToDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(this.getId());
        productResponseDto.setProductName(this.getName());
        productResponseDto.setProductUrl(this.getUrl());
        productResponseDto.setBaseDescription(this.getBaseDescription());
        productResponseDto.setDetailDescription(this.getDetailDescription());
        productResponseDto.setCategoryName(this.getCategory().getCategoryName());
        productResponseDto.setSupplierName(this.getSupplier().getName());
        productResponseDto.setImage(this.getImage());
        productResponseDto.setPrice(this.getPrice());
        productResponseDto.setDiscount(this.getDiscount());
        productResponseDto.setFinalPrice(this.getFinalPrice());
        productResponseDto.setNumberOfReviews(this.getReviews().size());
        productResponseDto.setRating(this.getRating());
        productResponseDto.setAmount(this.getAmount());

        List<ResponseReviewDto> reviews = this.getReviews().stream().map(rv -> rv.convertReviewToReviewDto()).collect(Collectors.toList());;
        productResponseDto.setReviews(reviews);
        return productResponseDto;
    }

    public ProductResponseDto convertToDtoNotIncludeReviews() {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId(this.getId());
        productResponseDto.setProductName(this.getName());
        productResponseDto.setProductUrl(this.getUrl());
        productResponseDto.setBaseDescription(this.getBaseDescription());
        productResponseDto.setDetailDescription(this.getDetailDescription());
        productResponseDto.setCategoryName(this.getCategory().getCategoryName());
        productResponseDto.setSupplierName(this.getSupplier().getName());
        productResponseDto.setImage(this.getImage());
        productResponseDto.setPrice(this.getPrice());
        productResponseDto.setDiscount(this.getDiscount());
        productResponseDto.setFinalPrice(this.getFinalPrice());
        productResponseDto.setNumberOfReviews(this.getReviews().size());
        productResponseDto.setRating(this.getRating());
        productResponseDto.setAmount(this.getAmount());
        return productResponseDto;
    }

}
