package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.RegisterAccountRequest;
import com.nguyenpham.oganicshop.dto.UserDto;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.service.UserService;
import com.nguyenpham.oganicshop.util.Utitity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
public class LoginRegisterControllerApi {

    private UserService userService;

    @Autowired
    public LoginRegisterControllerApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/registerAccount")
    public boolean regisAccount(@Valid @RequestBody RegisterAccountRequest accountRequest, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {

        if (userService.register(accountRequest)) {
            User saveUser = userService.findUserByEmail(accountRequest.getEmail());
            if (saveUser != null) {
                String siteURL = Utitity.getSiteURL(request);
                userService.sendVerificationEmail(saveUser, siteURL);
                return true;
            }
        }
        return false;
    }

    @GetMapping("/api/verifyAccount")
    public String verifyAccount(@Param("code") String code) {
        boolean verified = userService.verify(code);
        return verified ? "Verification Succeeded!" : "Verification Failed";
    }
}
