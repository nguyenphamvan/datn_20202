package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders", "reviews", "addresses", "ratings"})
@ToString(exclude = {"orders", "reviews", "addresses", "ratings"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    @Column(name = "fullname")
    private String fullName;
    private Date birthday;
    private String gender;
    private String phone;
    private String wishlist;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    private boolean enabled;
    private boolean blocked;
    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Order> orders;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Review> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Address> addresses;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Rating> ratings;

    public void addOrder(Order order) {
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        this.orders.add(order);
    }

    public void addReview(Review review) {
        if (this.reviews == null) {
            this.reviews = new HashSet<>();
        }
        this.reviews.add(review);
    }

    public void addAddress(Address address) {
        if (this.addresses == null) {
            this.addresses = new HashSet<>();
        }
        this.addresses.add(address);
    }

    public void addRating(Rating rating) {
        if (this.ratings == null) {
            this.ratings = new HashSet<>();
        }
        this.ratings.add(rating);
    }

}
