package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ProductDto;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.User;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public interface UserService {

    User findUserByEmail (String email);
    User findUserById (Long id);
    boolean register (UserDto userRequest);
    boolean checkOldPassword(String rawOldPassword);
    UserDto getInfoAccount();
    UserDto updateInfo (UserDto userRequest);
    UserDto updateShippingAddress (String newAddress, String phone);
    Set<ProductDto> getWishlists();
    void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;
    boolean verify(String verificationCode);

}
