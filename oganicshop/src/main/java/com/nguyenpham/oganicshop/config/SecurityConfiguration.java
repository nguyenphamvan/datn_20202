package com.nguyenpham.oganicshop.config;

import com.nguyenpham.oganicshop.security.UserDetailServiceImpl;
import com.nguyenpham.oganicshop.security.oauth2.CustomOAuth2UserService;
import com.nguyenpham.oganicshop.security.oauth2.Oauth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomOAuth2UserService oauth2UserService;
//    @Autowired
//    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        return new DefaultHttpFirewall();
    }

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

        http.authorizeRequests().
                antMatchers("/oauth2/**").permitAll().
                antMatchers("/signup", "/api/registerAccount", "/api/verifyAccount").permitAll()
                // Các yêu cầu phải login với vai trò user hoặc admin
                // Nếu chưa login, nó sẽ redirect tới trang /login.
                .antMatchers("/login", "/nhan-xet-san-pham-ban-da-mua", "/sales/**", "/checkout.html", "/add-product-review",
                        "/customer/**", "/api/customer/**", "/sales/**", "/api/sales/**")
                .access("hasAnyRole('USER', 'ADMIN')")
                // Các trang chỉ dành cho ADMIN
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
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
                .logoutSuccessUrl("/logout_success").and()
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(oauth2UserService)
//                .and().successHandler(oauth2LoginSuccessHandler);
                .exceptionHandling().accessDeniedPage("/403");
    }
}