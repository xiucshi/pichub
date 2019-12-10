package com.cong.config;

import com.cong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

/**
 * @Date : 2019/11/6
 * @Author : xiuc_shi
 **/
@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {
    @Lazy
    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private Map<String, String> sessionMap;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserInfoHandlerMethodArgumentResolver(userService, sessionMap));
    }
}
