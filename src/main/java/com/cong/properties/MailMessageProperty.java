package com.cong.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Date : 2019/11/7
 * @Author : xiuc_shi
 **/
@ConfigurationProperties(prefix = "mail")
public class MailMessageProperty {
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
