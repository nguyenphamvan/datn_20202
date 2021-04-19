package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.oganicshop.dto.UserDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders", "reviews", "shippingAddresses"})
@ToString(exclude = {"orders", "reviews", "shippingAddresses"})
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
    private Set<ShippingAddress> shippingAddresses;

    // should user moderMapper
    public UserDto convertUserToUserDto() {
        UserDto userDto = new UserDto();
        userDto.setRole(this.getRole().replace("ROLE_", ""));
        userDto.setId(this.getId());
        userDto.setEmail(this.getEmail());
        userDto.setFullName(this.getFullName());
        userDto.setPhone(this.getPhone());
        userDto.setBirthday(this.getBirthday());
        userDto.setGender(this.getGender());
        userDto.setCreatedDate(this.getCreatedAt());
        userDto.setEnabled(this.isEnabled());
        userDto.setBlocked(this.isBlocked());
        return userDto;
    }

}
