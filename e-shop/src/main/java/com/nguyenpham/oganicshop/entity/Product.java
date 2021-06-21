package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(exclude={"category" , "reviews"})
@ToString(exclude = {"category" , "reviews"})

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String productUrl;
    @Column(nullable = false)
    private String authors;
    @Column(nullable = false)
    private String originalPublicationYear;
    private int ratingsCount;
    private float weightedRating;
    @Column(nullable = false)
    private String mainImage;
    @Column(nullable = false)
    private String smallImage;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double discount;
    @Column(nullable = false)
    private Double finalPrice;
    private double averageRating;
    private int amount;
    private boolean stopBusiness;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Review> reviews;
}
