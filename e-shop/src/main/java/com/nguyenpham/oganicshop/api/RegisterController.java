package com.nguyenpham.oganicshop.api;

import com.nguyenpham.oganicshop.dto.AccountRequest;
import com.nguyenpham.oganicshop.entity.User;
import com.nguyenpham.oganicshop.service.EmailSender;
import com.nguyenpham.oganicshop.service.UserService;
import com.nguyenpham.oganicshop.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
public class RegisterController {

    private UserService userService;
    private EmailSender emailSender;

    @Autowired
    public RegisterController(UserService userService, EmailSender emailSender) {
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @PostMapping("/api/registerAccount")
    public boolean regisAccount(@Valid @RequestBody AccountRequest accountRequest, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {

        if (userService.register(accountRequest)) {
            User saveUser = userService.findUserByEmail(accountRequest.getEmail());
            if (saveUser != null) {
                String siteURL = Utils.getSiteURL(request);
                emailSender.sendVerificationEmail(saveUser, siteURL);
                return true;
            }
        }
        return false;
    }
}
