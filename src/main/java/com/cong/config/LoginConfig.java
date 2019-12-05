package com.cong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date : 2019/11/5
 * @Author : xiuc_shi
 **/
@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(sessionMap()))
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/index.html","/css/**","/content/**","/js/**");

    }

    @Bean
    public Map<String,String> sessionMap(){
        return new HashMap<>();
    }
}
