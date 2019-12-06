package com.cong.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date : 2019/11/7
 * @Author : xiuc_shi
 **/

@Slf4j
@Component(value = "myMailSender")
@Data
public class MailSender implements Runnable{
    @Autowired
    private SimpleMailMessage message;
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private List<String> mainContainer;

    public void sendMail(){
        message.setText(StringUtils.join(mainContainer,"\n"));
        message.setSubject("Pichub登录信息");
        sender.send(message);
        log.info("View info is sent to xiuc_shi@163.com");
        mainContainer.clear();
    }

    @Override
    public void run() {
        sendMail();
    }
}
