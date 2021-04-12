package com.nguyenpham.oganicshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.oganicshop.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders", "shippingAddresses"})
@ToString(exclude = {"orders", "shippingAddresses"})
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
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Order> orders;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ShippingAddress> shippingAddresses;

    // should user moderMapper
    public UserDto convertUserToUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(this.getId());
        userDto.setEmail(this.getEmail());
        userDto.setFullName(this.getFullName());
        userDto.setPhone(this.getPhone());
        userDto.setBirthday(this.getBirthday());
        userDto.setGender(this.getGender());
        return userDto;
    }

}
