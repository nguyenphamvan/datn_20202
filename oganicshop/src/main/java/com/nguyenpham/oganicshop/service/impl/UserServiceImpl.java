package com.nguyenpham.oganicshop.service.impl;

import com.nguyenpham.oganicshop.constant.Provider;
import com.nguyenpham.oganicshop.converter.AddressConverter;
import com.nguyenpham.oganicshop.converter.ProductConverter;
import com.nguyenpham.oganicshop.converter.UserConverter;
import com.nguyenpham.oganicshop.dto.*;
import com.nguyenpham.oganicshop.entity.Address;
import com.nguyenpham.oganicshop.entity.Order;
import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.exception.UserNotFoundException;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import com.nguyenpham.oganicshop.repository.ShippingAddressRepository;
import com.nguyenpham.oganicshop.repository.UserRepository;
import com.nguyenpham.oganicshop.security.MyUserDetail;
import com.nguyenpham.oganicshop.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
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
    private JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserConverter userConverter, UserRepository userRepository, ProductRepository productRepository,
                           ShippingAddressRepository shippingAddressRepository, JavaMailSender mailSender) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.mailSender = mailSender;
    }

    @Override
    public long countNumberAccount() {
        return userRepository.count();
    }

    @Override
    public List<UserResponseDto> findAll(long currentUserId) {
        return userRepository.findAllByIdIsNot(currentUserId).stream().map(u -> userConverter.entityToDto(u)).collect(Collectors.toList());
    }

    @Override
    public Object getInfoDetailAccount(long userId) {
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
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
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
    public void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String subject = "Vui lòng xác minh đăng ký của bạn";
        String senderName = "Admin Web shop";
        String mailContent = "<p>Cảm ơn  <b>" + user.getFullName() + "</b>,</p>";
        String verifyURL = siteURL + "/api/verifyAccount?code=" + user.getVerificationCode();

        mailContent += "<p>Vui lòng nhấp vào liên kết bên dưới để xác minh đăng ký của bạn :</p>";
        mailContent += "<a href=\"" + verifyURL + "\">Xác nhận</a>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("nguyenphamvan1998@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        mailSender.send(message);
    }

    @Override
    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String senderName = "Web shop support";
        String subject = "Here's the link to reset your password";

        String content = "<p>Chào bạn,</p>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>"
                + "<p>Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn</p>"
                + "<p><a href=\"" + link + "\">Thay đổi mật khẩu của tôi</a></p>"
                + "<br>"
                + "<p>Bỏ qua email này nếu bạn nhớ mật khẩu của mình, "
                + "hoặc bạn đã không thực hiện yêu cầu.</p>";

        helper.setFrom("nguyenphamvan1998@gmail.com", senderName);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
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
    public void doBlockAccount(long userId, boolean isBlock) {
        User user = userRepository.findById(userId).get();
        user.setBlocked(isBlock);
        userRepository.save(user);
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
    @Transactional
    public AddressResponseDto addShippingAddress(AddressRequestDto request) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        Address address = new Address();
        address = saveAddress(address, request, userDb);
        if (request.isDefault()) {
            shippingAddressRepository.setAddressDefault(address.getId());
        }
        return new AddressConverter().entityToDto(address);
    }

    @Override
    @Transactional
    public AddressResponseDto updateShippingAddress(AddressRequestDto request) {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User userDb = userRepository.findById(user.getId()).get();
        Address address = shippingAddressRepository.findById(request.getId()).get();
        address = saveAddress(address, request, userDb);
        if (request.isDefault()) {
            shippingAddressRepository.setAddressDefault(request.getId());
        }
        return new AddressConverter().entityToDto(address);
    }

    @Override
    public List<AddressResponseDto> getShippingAddress() {
        User user = ((MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Address> listAddresses = shippingAddressRepository.findAllByUserId(user.getId());
        AddressConverter converter = new AddressConverter();
        List<AddressResponseDto> resShippingAddress = listAddresses.stream().map(ad -> converter.entityToDto(ad)).collect(Collectors.toList());
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
