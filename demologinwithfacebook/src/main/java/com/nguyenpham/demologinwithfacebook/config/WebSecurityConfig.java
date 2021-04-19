package com.nguyenpham.demologinwithfacebook.config;

import com.nguyenpham.demologinwithfacebook.security.oauth2.CustomOAuth2UserService;
import com.nguyenpham.demologinwithfacebook.security.UserDetailServiceImpl;
import com.nguyenpham.demologinwithfacebook.security.oauth2.Oauth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2UserService oauth2UserService;
    @Autowired
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/users").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic().and()
                .formLogin()
                    .permitAll()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                .logout().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(oauth2UserService)
                    .and().successHandler(oauth2LoginSuccessHandler);
    }
}
