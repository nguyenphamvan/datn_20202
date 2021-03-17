package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.repository.UserRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.UserService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean register(UserDto userRequest) {
        User user = new User();
        user.setEmail(userRequest.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setEnabled(false);
        user.setRole("ROLE_USER");
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean checkOldPassword(String rawOldPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (bCryptPasswordEncoder.matches(rawOldPassword, user.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public UserDto getInfoAccount() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        String userEmail = user.getEmail();
        return user.convertUserToUserDto();
    }

    @Override
    @Transactional
    public UserDto updateInfo(UserDto userRequest) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        userDb.setFullName(userRequest.getFullName());
        userDb.setPhone(userRequest.getPhone());
        userDb.setGender(userRequest.getGender());
        userDb.setBirthday(userRequest.getBirthday());
        if (userRequest.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userDb.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        }
        User userUpdated = userRepository.save(userDb);
        return userUpdated.convertUserToUserDto();
    }

    @Override
    @Transactional
    public UserDto updateShippingAddress(String newAddress, String phone) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        userDb.setAddress(newAddress);
        userDb.setPhone(phone);
        User userUpdated = userRepository.save(userDb);
        return userUpdated.convertUserToUserDto();
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
    }

    @Override
    public boolean verify(String verificationCode) {
        return false;
    }
}
