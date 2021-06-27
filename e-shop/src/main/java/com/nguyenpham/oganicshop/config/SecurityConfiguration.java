package com.nguyenpham.oganicshop.config;

import com.nguyenpham.oganicshop.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
@Order(2)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Bean("userDetailService1")
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
                // Các yêu cầu phải login với vai trò user hoặc admin
                // Nếu chưa login, nó sẽ redirect tới trang /login.
                .antMatchers("/nhan-xet-san-pham-ban-da-mua", "/sales/**", "/checkout.html", "/add-product-review",
                        "/customer/**", "/api/customer/**", "/sales/**", "/api/sales/**")
                .access("hasAnyRole('USER', 'ADMIN')")
                .anyRequest().permitAll()
                .and()
                .httpBasic().and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/doLogin")
                    .defaultSuccessUrl("/")//
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/logout_success").and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
    }
}