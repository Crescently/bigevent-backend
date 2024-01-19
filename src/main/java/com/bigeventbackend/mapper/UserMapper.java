package com.bigeventbackend.mapper;

import com.bigeventbackend.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 查询用户信息
    @Select("select *from user where username = #{username}")
    User getUserInfoByName(String username);

    // 添加用户信息
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values(#{username},#{password},now(),now())")
    void addUserInfo(String username, String password);
}
