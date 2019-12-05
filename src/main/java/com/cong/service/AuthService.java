package com.cong.service;

import com.cong.bean.User;
import com.cong.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @Date : 2019/11/4
 * @Author : xiuc_shi
 **/
@Service
public class AuthService {
    @Resource
    private AuthMapper authMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public Optional<String> login(String username, String password){
        String realPassword = authMapper.getPassword(username);

        if (realPassword == null){
            return Optional.of("用户不存在");
        }else if (!bCryptPasswordEncoder.matches(password, realPassword)){
            return Optional.of("密码不正确");
        }
        return Optional.of("登录成功");
    }

    public User getUser(String username) {
        return authMapper.getUser(username);
    }
}
