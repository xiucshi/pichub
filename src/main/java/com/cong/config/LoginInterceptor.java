package com.cong.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Date : 2019/11/5
 * @Author : xiuc_shi
 **/
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 手机端的session会出现问题，所以用map保存session信息
     */
    private Map<String,String> map;
    public LoginInterceptor(Map<String, String> map){
        this.map = map;

    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object userInfo = session.getAttribute("username");
        String client = request.getHeader("User-Agent").split("\\)")[0] + ")";
        if (userInfo == null && map.get(client) == null){
            log.info("未登录");
            return false;
        } else {
         //  log.info("已登录,用户信息为：" + session.getAttribute("username"));
        }
        return true;
    }
}
