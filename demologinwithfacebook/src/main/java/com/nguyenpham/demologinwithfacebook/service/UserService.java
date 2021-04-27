package com.nguyenpham.demologinwithfacebook.service;

import com.nguyenpham.demologinwithfacebook.constant.Provider;
import com.nguyenpham.demologinwithfacebook.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User findUserByEmail(String email);
    User registerNewUserAfterOAuthLoginSuccess(String email, String fullName, Provider provider);
    User updateExistCustomerAfterOAuthLoginSuccess(String email, String fullName);
}
