package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.oganicshop.dto.ProductDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

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
    private String productName;
    @Column(nullable = false, unique = true)
    private String productUrl;
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
    private int numberOfReviews;
    private int amount;

    @JsonIgnore
    @CreationTimestamp
    private Date createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Review> reviews;

    public ProductDto convertProductToProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(this.getId());
        productDto.setProductName(this.getProductName());
        productDto.setProductUrl(this.getProductUrl());
        productDto.setBaseDescription(this.getBaseDescription());
        productDto.setDetailDescription(this.getDetailDescription());
        productDto.setCategoryName(this.getCategory().getCategoryName());
        productDto.setSupplierName(this.getSupplier().getName());
        productDto.setImage(this.getImage());
        productDto.setPrice(this.getPrice());
        productDto.setDiscount(this.getDiscount());
        productDto.setFinalPrice(this.getFinalPrice());
        productDto.setNumberOfReviews(this.getNumberOfReviews());
        productDto.setRating(this.getRating());
        productDto.setAmount(this.getAmount());
        return productDto;
    }

}
