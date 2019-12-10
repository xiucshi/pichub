package com.cong.config;

import com.cong.annotation.UserInfo;
import com.cong.bean.User;
import com.cong.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Date : 2019/11/6
 * @Author : xiuc_shi
 **/

public class UserInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private UserService userService;
    private Map<String, String> sessionMap;
    public UserInfoHandlerMethodArgumentResolver(UserService userService, Map<String, String> sessionMap){
        this.userService = userService;
        this.sessionMap = sessionMap;
    }
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.hasParameterAnnotation(UserInfo.class) && methodParameter.getParameterType().equals(User.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String username = (String) request.getSession().getAttribute("username");
        User user = null;
        if(username != null){
            user = userService.getUser(username);
        } else {
            String client = request.getHeader("User-Agent").split("\\)")[0] + ")";
            user = userService.getUser(sessionMap.get(client));
        }
        return user;
    }
}
