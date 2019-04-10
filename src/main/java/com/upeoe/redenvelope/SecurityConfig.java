package com.upeoe.redenvelope;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author upeoe
 * @create 2019/4/9 17:59
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final AuthFilter authFilter = new AuthFilter(authenticationManager());

        http.cors().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/token/**").permitAll()
                .antMatchers("/api/v1/**").authenticated()
                .and().httpBasic()
                .and().addFilter(authFilter);
    }

}
