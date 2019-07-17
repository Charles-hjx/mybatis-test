package com.hjx.springbootmybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**","/mybatis","/login","/index/**","/api/user/save","/api/user/find/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/index/login.html")
                .loginProcessingUrl("/login");
    }


}
