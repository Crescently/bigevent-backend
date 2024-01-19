package com.bigeventbackend.service.impl;

import com.bigeventbackend.mapper.UserMapper;
import com.bigeventbackend.pojo.entity.User;
import com.bigeventbackend.service.UserService;
import com.bigeventbackend.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfoByName(String username) {
        // 查询数据库，根据用户名获取用户信息
        return userMapper.getUserInfoByName(username);
    }

    @Override
    public void register(String username, String password) {
        // 首先对密码进行加密，保证安全性
        String md5String = Md5Util.getMD5String(password);
        userMapper.addUserInfo(username, md5String);
    }
}
