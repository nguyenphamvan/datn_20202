package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.repository.UserRepository;
import com.nguyenpham.oganicshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
