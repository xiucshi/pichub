package com.cong.config;

import com.cong.properties.MailMessageProperty;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;


import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2019/11/7
 * @Author : xiuc_shi
 **/
@Configuration
@EnableConfigurationProperties(MailMessageProperty.class)
public class SimpleMailConfig {
    @Autowired
    private MailMessageProperty messageProperty;

    @Bean
    public SimpleMailMessage simpleMailMessage(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(messageProperty.getFrom());
        message.setTo(messageProperty.getTo());
        return message;
    }
    @Bean(name = "mailContainer",autowire = Autowire.BY_NAME)
    public List<String> getMailContainer(){
        return new ArrayList<>();
    }

}
