package com.nguyenpham.demologinwithfacebook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyenpham.demologinwithfacebook.constant.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    private String fullname;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}
