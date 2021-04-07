package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.dto.ProductDto;
import com.nguyenpham.oganicshop.dto.ShippingAddressDto;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.ShippingAddress;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.repository.ShippingAddressRepository;
import com.nguyenpham.oganicshop.repository.UserRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.UserService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository, ShippingAddressRepository shippingAddressRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shippingAddressRepository = shippingAddressRepository;
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
    public ShippingAddressDto addShippingAddress(ShippingAddressDto request) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        ShippingAddress shippingAddress = new ShippingAddress();
        saveAddress(shippingAddress, request, userDb);
        if (request.isDefault()) {
            shippingAddressRepository.setAddressDefault(shippingAddress.getId());
        }
        request.setId(shippingAddress.getId());
        return request;
    }

    @Override
    @Transactional
    public ShippingAddressDto updateShippingAddress(ShippingAddressDto request){
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        ShippingAddress shippingAddress = shippingAddressRepository.findById(request.getId()).get();
        saveAddress(shippingAddress, request, userDb);
        if (request.isDefault()) {
            shippingAddressRepository.setAddressDefault(request.getId());
        }
        return request;
    }

    @Override
    public List<ShippingAddressDto> getShippingAddress() {
        List<ShippingAddress> listShippingAddress = shippingAddressRepository.findAll();
        List<ShippingAddressDto> resShippingAddress = listShippingAddress.stream().map(ad -> ad.convertToDto()).collect(Collectors.toList());
        Collections.sort(resShippingAddress);
        return resShippingAddress;
    }

    @Override
    public boolean deleteShippingAddress(long addressId) {
        try {
            shippingAddressRepository.deleteById(addressId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Set<ProductDto> getWishlists() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Set<Long> idWishlistProductsSet = getSetIdProductWishlist(user);
        Set<ProductDto> wishlistProducts = new HashSet<>();
        for (Long productId : idWishlistProductsSet) {
            Product product = productRepository.findById(productId).get();
            wishlistProducts.add(product.convertToDto());
        }

        return wishlistProducts;
    }

    @Override
    public boolean addProductToWishlist(long productId) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Set<Long> idWishlistProductsSet = getSetIdProductWishlist(user);
        if (productRepository.findById(productId).get() != null) { // check product exits in db
            idWishlistProductsSet.add(productId);
        } else {
            return false;
        }
        String idWishlistStr = StringUtils.join(idWishlistProductsSet, ",");
        user.setWishlist(idWishlistStr);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean removeProductFromWishlist(long productId) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Set<Long> idWishlistProductsSet = getSetIdProductWishlist(user);
        if (idWishlistProductsSet.contains(productId)) {
            idWishlistProductsSet.remove(productId);
        } else {
            return false;
        }
        String idWishlistStr = StringUtils.join(idWishlistProductsSet, ",");
        user.setWishlist(idWishlistStr);
        userRepository.save(user);
        return true;
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
    }

    @Override
    public boolean verify(String verificationCode) {
        return false;
    }

    public Set<Long> getSetIdProductWishlist(User user) {
        Set<Long> idWishlistProductsSet = null;
        if (user.getWishlist() == null) {
            idWishlistProductsSet = new HashSet<>();
        } else if ("".equals(user.getWishlist())) {
            idWishlistProductsSet = new HashSet<>();
        } else {
            idWishlistProductsSet = Stream.of(user.getWishlist().split(",")).map(Long::valueOf).collect(Collectors.toSet());
        }
        return idWishlistProductsSet;
    }

    public void saveAddress(ShippingAddress shippingAddress, ShippingAddressDto request, User user){
        shippingAddress.setContactReceiver(request.getContactReceiver());
        shippingAddress.setContactAddress(request.getContactAddress());
        shippingAddress.setContactPhone(request.getContactPhone());
        shippingAddress.setAddrDefault(request.isDefault());
        shippingAddress.setUser(user);
        shippingAddressRepository.save(shippingAddress);
    }
}
