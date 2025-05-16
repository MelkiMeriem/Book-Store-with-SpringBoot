package com.example.bookstore.config;

import com.example.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home", "/books", "/register", "/login", "/h2-console/**").permitAll()
                .antMatchers("/books/add", "/books/edit/**", "/books/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/books")
                .permitAll()
                .and()
            .rememberMe()
                .key("uniqueAndSecret")
                .tokenValiditySeconds(86400) // 1 day
                .userDetailsService(userService)
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
                .and()
            .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .and()
            .headers()
                .frameOptions()
                .sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder);
    }
} 