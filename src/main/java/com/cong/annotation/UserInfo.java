package com.cong.annotation;

import java.lang.annotation.*;

/**
 * @Date : 2019/11/6
 * @Author : xiuc_shi
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface UserInfo {

}
