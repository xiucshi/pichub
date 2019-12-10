package com.cong.bean;

import lombok.*;

import java.util.List;

/**
 * @Date : 2019/11/6
 * @Author : xiuc_shi
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private Integer id;
    private String username;
    private String password;
    private String headImgUrl;
    private String userType;
    private String category;
}
