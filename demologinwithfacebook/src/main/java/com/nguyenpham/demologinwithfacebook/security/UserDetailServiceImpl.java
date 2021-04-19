package com.nguyenpham.demologinwithfacebook.security;

import com.nguyenpham.demologinwithfacebook.entity.User;
import com.nguyenpham.demologinwithfacebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("could not find user !");
        }
        return new MyUserDetail(user);
    }
}
