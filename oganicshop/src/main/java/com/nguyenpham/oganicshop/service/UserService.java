package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.dto.ProductDto;
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

    User findUserByEmail (String email);
    User getByResetPasswordToken(String token);
    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;
    void updatePassword(User user, String newPassword);
    User findUserById (Long id);
    boolean register (RegisterAccountRequest accountRequest);
    boolean checkOldPassword(String rawOldPassword);
    UserDto getInfoAccount();
    UserDto updateInfo (UserDto userRequest);
    ShippingAddressDto addShippingAddress(ShippingAddressDto request);
    List<ShippingAddressDto> getShippingAddress();
    ShippingAddressDto updateShippingAddress (ShippingAddressDto request);
    boolean deleteShippingAddress(long addressId);
    Set<ProductDto> getWishlists();
    boolean addProductToWishlist(long productId);
    boolean removeProductFromWishlist(long productId);
    void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException;
    void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException;
    boolean verify(String verificationCode);

}
