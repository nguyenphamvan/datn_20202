package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String fullName;
    private String gender;
    private Date birthday;
    private String address;
    private String phone;
}
