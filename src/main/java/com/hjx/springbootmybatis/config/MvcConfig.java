package com.hjx.springbootmybatis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: hjx
 * @Date: 2019/7/8
 * @Version 1.0
 */
@Configuration
@EnableSpringDataWebSupport
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index/login");
        registry.addViewController("/mybatis").setViewName("index/register");
        registry.addViewController("/index/login").setViewName("index/login");
    }
}
