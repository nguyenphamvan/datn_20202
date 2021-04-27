package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String role;
    private String fullName;
    private String gender;
    private Date birthday;
    private String phone;
    private Date createdDate;
    private boolean enabled;
    private boolean blocked;
}