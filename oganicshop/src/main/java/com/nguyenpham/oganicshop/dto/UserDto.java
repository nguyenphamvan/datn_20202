package com.nguyenpham.oganicshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    private String phone;
}
