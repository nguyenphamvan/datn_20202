package com.nguyenpham.oganicshop.service;

import com.nguyenpham.oganicshop.constant.Provider;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService {

    long countNumberAccount();
    List<UserResponse> getAllAccount();
    Object getInfoDetailAccount(long userId);
    User findUserById (Long id);
    User findUserByEmail (String email);
    User getUserByResetToken(String token);
    void updateResetToken(String token, String email) throws UserNotFoundException;
    void updatePassword(User user, String newPassword);
    boolean register (AccountRequest accountRequest);
    boolean checkOldPassword(String rawOldPassword);
    UserResponse getInfoAccount();
    UserResponse updateInfoAccount (UserRequest userRequest);
    List<AddressResponse> getListAddress();
    boolean addAddress(AddressRequest request);
    boolean updateAddress (AddressRequest request);
    boolean deleteAddress(long addressId);
    Set<ProductResponse> getWishlists(User user);
    boolean addProductToWishlist(long productId);
    boolean removeProductFromWishlist(long productId);
    boolean verify(String verificationCode);
    void doBlockAccount(long userId, boolean isBlock);
    boolean setRoleAccount(long userId, String role);
    User registerNewUserAfterOAuthLoginSuccess(String email, String fullName, Provider provider);
    User updateExistCustomerAfterOAuthLoginSuccess(String email, String fullName);

}
