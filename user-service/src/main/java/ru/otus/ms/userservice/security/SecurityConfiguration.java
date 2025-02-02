package ru.otus.ms.userservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${security-settings.url-white-list}")
    private String[] urlWhiteList;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(urlWhiteList).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin@mail.ru")
                .password("$2y$12$tapTzdZbfE04o7S4BNY2IO4fOvwIkHitcG31voXXyWEtw3EvBoqVy")
                .roles("ADMIN", "USER")
                .and()
                .withUser("user@mail.ru")
                .password("$2y$12$tapTzdZbfE04o7S4BNY2IO4fOvwIkHitcG31voXXyWEtw3EvBoqVy")
                .roles("USER");

    }

}
