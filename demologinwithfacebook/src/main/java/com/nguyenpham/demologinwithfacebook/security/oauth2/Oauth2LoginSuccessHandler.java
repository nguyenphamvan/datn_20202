package com.nguyenpham.demologinwithfacebook.security.oauth2;

import com.nguyenpham.demologinwithfacebook.constant.Provider;
import com.nguyenpham.demologinwithfacebook.entity.User;
import com.nguyenpham.demologinwithfacebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String fullName = oAuth2User.getName();
        System.out.println("customer's email : " + email + " , Name : " + fullName);

        User user = userService.findUserByEmail(email);
        if (user == null) {
            userService.registerNewUserAfterOAuthLoginSuccess(email, fullName, Provider.FACEBOOK);
        } else {
            userService.updateExistCustomerAfterOAuthLoginSuccess(email, fullName, Provider.LOCAL);
        }
        super.onAuthenticationSuccess(request, response, authentication);

    }
}
