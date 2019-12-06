package com.cong.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Date : 2019/11/10
 * @Author : xiuc_shi
 **/
@Configuration
public class ResourceHandlerConfig implements WebMvcConfigurer{
    @Value("${videos.location}")
    private String VIDEO_SAVE_PATH;
    @Value("${images.location}")
    private String PICTURE_SAVE_PATH;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/videos/**","/picture/**")
                .addResourceLocations("file:" + VIDEO_SAVE_PATH + "/","file:" + PICTURE_SAVE_PATH + "/");

    }
}
