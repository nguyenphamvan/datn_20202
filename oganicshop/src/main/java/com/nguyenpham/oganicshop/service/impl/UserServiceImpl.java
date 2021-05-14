package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Provider;
import com.nguyenpham.oganicshop.converter.AddressConverter;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.converter.UserConverter;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.*;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.repository.RatingRepository;
import com.nguyenpham.oganicshop.repository.ShippingAddressRepository;
import com.nguyenpham.oganicshop.repository.UserRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserConverter userConverter;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private ShippingAddressRepository shippingAddressRepository;
    private RatingRepository ratingRepository;

    @Autowired
    public UserServiceImpl(UserConverter userConverter, UserRepository userRepository, ProductRepository productRepository,
                           ShippingAddressRepository shippingAddressRepository, RatingRepository ratingRepository) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public long countNumberAccount() {
        return userRepository.count();
    }

    @Override
    public List<UserResponseDto> getAllUser() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return userRepository.findAllByIdIsNot(user.getId()).stream().map(u -> userConverter.entityToDto(u)).collect(Collectors.toList());
    }

    @Override
    public Object getInfoDetailUser(long userId) {
        Map<String, Object> objectMap = new HashMap<>();
        User user = userRepository.findById(userId).get();
        long numbersOfOrder = 0;
        long maxOrderPrice = 0;
        long numbersOfReview = 0;
        long numbersOfWishlist = 0;
        if (user.getWishlist() != null) {
            numbersOfWishlist = user.getWishlist().split(",").length;
        }
        if (user.getOrders().size() > 0) {
            numbersOfOrder = user.getOrders().stream().count();
        }
        if (user.getOrders().size() > 0) {
            maxOrderPrice = user.getOrders().stream().max(Comparator.comparing(Order::getTotal)).get().getTotal();
        }
        if (user.getReviews().size() > 0) {
            numbersOfReview = user.getReviews().stream().count();
        }
        objectMap.put("account", userConverter.entityToDto(user));

        AddressConverter converter = new AddressConverter();
        objectMap.put("shippingAddress", user.getAddresses().stream().map(a -> converter.entityToDto(a)).collect(Collectors.toList()));
        objectMap.put("numbersOfOrder", numbersOfOrder);
        objectMap.put("maxOrderPrice", maxOrderPrice);
        objectMap.put("numbersOfReview", numbersOfReview);
        objectMap.put("numbersOfWishlist", numbersOfWishlist);
        return objectMap;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByResetToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updateResetToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean register(RegisterAccountRequest accountRequest) {
        User user = new User();
        user.setFullName(accountRequest.getFullName());
        user.setEmail(accountRequest.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(accountRequest.getPassword()));
        user.setEnabled(false);
        user.setRole("ROLE_USER");
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean verify(String verificationCode) {
        User User = userRepository.findByVerificationCode(verificationCode);
        if (User == null || User.isEnabled()) {
            return false;
        } else {
            userRepository.setActive(true, User.getId());
            return true;
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void doBlockAccount(long userId, boolean isBlock) {
        User user = userRepository.findById(userId).get();
        user.setBlocked(isBlock);
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean updateRoleUser(long userId, String role) {
        try {
            User user = userRepository.findById(userId).get();
            user.setRole(role);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User registerNewUserAfterOAuthLoginSuccess(String email, String fullName, Provider provider) {
        User user = new User();
        user.setEmail(email);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public User updateExistCustomerAfterOAuthLoginSuccess(String email, String fullName) {
        return null;
    }

    @Override
    public void rateProduct(RequestReviewDto reviewDto, long userId) {
        try {
            RatingKey key = new RatingKey(userId, reviewDto.getProductId());
            if (ratingRepository.existsById(key)) {
                Rating oldRating = ratingRepository.findById(key).get();
                oldRating.setRatingScore(reviewDto.getRating());
                ratingRepository.save(oldRating);
            } else {
                User user = userRepository.findById(userId).get();
                Product product = productRepository.findById(reviewDto.getProductId()).get();
                Rating rating = new Rating();
                rating.setId(key);
                rating.setUser(user);
                rating.setProduct(product);
                rating.setRatingScore(reviewDto.getRating());
                ratingRepository.save(rating);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkOldPassword(String rawOldPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (bCryptPasswordEncoder.matches(rawOldPassword, user.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public UserResponseDto getInfoAccount() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return userConverter.entityToDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateInfoAccount(UserRequestDto userRequest) {
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
        return userConverter.entityToDto(userUpdated);
    }

    @Override
    public List<AddressResponseDto> getAddress() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Address> listAddresses = shippingAddressRepository.findAllByUserId(user.getId());
        AddressConverter converter = new AddressConverter();
        List<AddressResponseDto> resShippingAddress = listAddresses.stream().map(ad -> converter.entityToDto(ad)).collect(Collectors.toList());
        Collections.sort(resShippingAddress);
        return resShippingAddress;
    }

    @Override
    @Transactional
    public boolean addAddress(AddressRequestDto request) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        try {
            Address address = new Address();
            address = saveAddress(address, request, userDb);
            if (request.isDefault()) {
                shippingAddressRepository.setAddressDefault(address.getId());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateAddress(AddressRequestDto request) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        try {
            Address address = shippingAddressRepository.findById(request.getId()).get();
            address = saveAddress(address, request, userDb);
            if (request.isDefault()) {
                shippingAddressRepository.setAddressDefault(address.getId());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAddress(long addressId) {
        try {
            shippingAddressRepository.deleteById(addressId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Set<ProductResponseDto> getWishlists(User user) {
        Set<Long> idWishlistProductsSet = getSetIdProductWishlist(user);
        Set<ProductResponseDto> wishlistProducts = new HashSet<>();
        ProductConverter converter = new ProductConverter();
        for (Long productId : idWishlistProductsSet) {
            Product product = productRepository.findById(productId).get();
            wishlistProducts.add(converter.entityToDto(product));
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

    public Address saveAddress(Address address, AddressRequestDto request, User user) {
        address.setContactReceiver(request.getContactReceiver());
        address.setContactAddress(request.getContactAddress());
        address.setContactPhone(request.getContactPhone());
        address.setAddrDefault(request.isDefault());
        address.setUser(user);
        return shippingAddressRepository.save(address);
    }
}
