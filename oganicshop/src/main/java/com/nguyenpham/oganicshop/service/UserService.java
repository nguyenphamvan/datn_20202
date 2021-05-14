package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.constant.Provider;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.Rating;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

public interface UserService {

    long countNumberAccount();
    List<UserResponseDto> getAllUser();
    Object getInfoDetailUser(long userId);
    User findUserById (Long id);
    User findUserByEmail (String email);
    User getUserByResetToken(String token);
    void updateResetToken(String token, String email) throws UserNotFoundException;
    void updatePassword(User user, String newPassword);
    boolean register (RegisterAccountRequest accountRequest);
    boolean checkOldPassword(String rawOldPassword);
    UserResponseDto getInfoAccount();
    UserResponseDto updateInfoAccount (UserRequestDto userRequest);
    List<AddressResponseDto> getAddress();
    boolean addAddress(AddressRequestDto request);
    boolean updateAddress (AddressRequestDto request);
    boolean deleteAddress(long addressId);
    Set<ProductResponseDto> getWishlists(User user);
    boolean addProductToWishlist(long productId);
    boolean removeProductFromWishlist(long productId);
    boolean verify(String verificationCode);
    void doBlockAccount(long userId, boolean isBlock);
    boolean updateRoleUser(long userId, String role);
    User registerNewUserAfterOAuthLoginSuccess(String email, String fullName, Provider provider);
    User updateExistCustomerAfterOAuthLoginSuccess(String email, String fullName);
    void rateProduct(RequestReviewDto requestReviewDto, long userId);

}
