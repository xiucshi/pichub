package com.cong.mapper;

import com.cong.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Date : 2019/11/4
 * @Author : xiuc_shi
 **/
@Mapper
public interface AuthMapper {
    @Select("select password from user_tbl where username = #{username}")
    String getPassword(String username);

    @Select("select * from user_tbl where username = #{username}")
    User getUser(String username);
}
