package com.nguyenpham.oganicshop.config;

import com.nguyenpham.oganicshop.security.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {


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

        http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                    .loginPage("/loginAdmin")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .loginProcessingUrl("/admin/doLogin")
                    .defaultSuccessUrl("/admin")//
                    .failureUrl("/admin/doLogin?error=true")//
                .and()
                    .logout()
                    .logoutUrl("/admin/logout")
                    .deleteCookies("JSESSIONID")

                .and()
                .exceptionHandling()
                .accessDeniedPage("/403");
    }
}
