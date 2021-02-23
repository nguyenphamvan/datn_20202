package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.entity.User;

public interface UserService {

    User findUserByEmail(String email);
}
