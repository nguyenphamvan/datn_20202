package com.nguyenpham.demologinwithfacebook.service.impl;

import com.nguyenpham.demologinwithfacebook.constant.Provider;
import com.nguyenpham.demologinwithfacebook.entity.User;
import com.nguyenpham.demologinwithfacebook.repository.UserRepository;
import com.nguyenpham.demologinwithfacebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerNewUserAfterOAuthLoginSuccess(String email, String fullName, Provider provider) {
        User user = new User();
        user.setEmail(email);
        user.setFullname(fullName);
        user.setEnabled(true);
        user.setProvider(provider);
        return userRepository.save(user);
    }

    @Override
    public User updateExistCustomerAfterOAuthLoginSuccess(String email, String fullName) {
        User user = userRepository.findByEmail(email);
        user.setFullname(fullName);
        return userRepository.save(user);
    }
}
