package com.cong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Date : 2019/11/10
 * @Author : xiuc_shi
 **/
@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/videos/**","/picture/**")
                .addResourceLocations("file:src\\main\\resources\\static\\videos\\","file:src\\main\\resources\\static\\images\\");

    }
}
