package com.cong.controller;

import com.cong.bean.MailSender;
import com.cong.bean.User;
import com.cong.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @Date : 2019/11/4
 * @Author : xiuc_shi
 **/
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private List<String> mailContainer;
    @Autowired
    private ExecutorService threadPool;
    @Autowired
    private MailSender sender;

    @Autowired
    private Map<String,String> map;

//    @Value("${html.homepage}")
//    private String HOMEPAGE;
//    @Value("${html.index}")
//    private String INDEX;
    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletRequest request, HttpServletResponse response,
                                        @RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        System.out.println("username:" + username + " " + "password:" + password);
        Optional<String> loginInfo = userService.login(username, password);
        if(loginInfo.get().equals("登录成功")){
            request.getSession().setAttribute("username", username);
            String ip = request.getRemoteAddr();
            String client = request.getHeader("User-Agent").split("\\)")[0] + ")";
            map.put(ip,username);
            log.info(request.getRemoteAddr() + "  " + client + "登录：username->" + username);
            mailContainer.add(request.getSession().getAttribute("username") + " is logined.");
            mailContainer.add("IP地址：" + request.getRemoteAddr());
            mailContainer.add("设备：" + client);
            threadPool.execute(sender);
            return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList("登录成功")));
        }
        return ResponseEntity.status(401).body(new ArrayList<>(Arrays.asList("登录失败")));
    }
    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("username");
        String ip = request.getRemoteAddr();
        map.remove(ip);
        return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList("退出登录成功")));
    }


//    /**
//     * 充当路由
//     * @param request
//     * @param response
//     * @throws IOException
//     */
//    @GetMapping("/")
//    public void welcome(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        HttpSession session = request.getSession();
//        String client = request.getHeader("User-Agent").split("\\)")[0] + ")";
//        if (session.getAttribute("username") == null && map.get(client) == null){
//            response.sendRedirect(INDEX);
//        } else {
//            response.sendRedirect(HOMEPAGE);
//        }
//    }
    @GetMapping("/username")
    public ResponseEntity<Object> getLoginedUsername(HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null){
            return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList(username)));
        } else {
            String ip = request.getRemoteAddr();
            return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList(map.get(ip))));
        }
    }

    /**
     * 主要用于获取登录界面的头像
     * @param username
     * @return
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<Object> getUser(@PathVariable("username") String username){
        User user = userService.getUser(username);
        return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList(user)));
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getLoginedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null){
            return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList(userService.getUser(username))));
        } else {
            String ip = request.getRemoteAddr();
            return ResponseEntity.ok().body(new ArrayList<>(Arrays.asList(userService.getUser(map.get(ip)))));
        }
    }
}
