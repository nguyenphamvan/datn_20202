package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ProductResponseDto;
import com.nguyenpham.oganicshop.dto.RegisterAccountRequest;
import com.nguyenpham.oganicshop.dto.ShippingAddressDto;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserDto> findAll(long currentUserId);
    Object getInfoDetailAccount(long userId);
    User findUserById (Long id);
    User findUserByEmail (String email);
    User getByResetPasswordToken(String token);
    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;
    void updatePassword(User user, String newPassword);
    boolean register (RegisterAccountRequest accountRequest);
    boolean checkOldPassword(String rawOldPassword);
    UserDto getInfoAccount();
    UserDto updateInfoAccount (UserDto userRequest);
    List<ShippingAddressDto> getShippingAddress();
    ShippingAddressDto addShippingAddress(ShippingAddressDto request);
    ShippingAddressDto updateShippingAddress (ShippingAddressDto request);
    boolean deleteShippingAddress(long addressId);
    Set<ProductResponseDto> getWishlists(User user);
    boolean addProductToWishlist(long productId);
    boolean removeProductFromWishlist(long productId);
    void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;
    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
    boolean verify(String verificationCode);
    void doBlockAccount(long userId, boolean isBlock);

}
