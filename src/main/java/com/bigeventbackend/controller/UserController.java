package com.bigeventbackend.controller;

import com.bigeventbackend.constant.MessageConstant;
import com.bigeventbackend.constant.Result;
import com.bigeventbackend.pojo.entity.User;
import com.bigeventbackend.service.UserService;
import com.bigeventbackend.utils.JwtUtil;
import com.bigeventbackend.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated // 参数校验工具
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
        // 1.查询用户名是否已经被注册
        User user = userService.getUserInfoByName(username);
        if (user == null) {
            // 2.如果没有，进行注册
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error(MessageConstant.USERNAME_EXIST);
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
        // 1.根据用户名查询用户
        User loginUser = userService.getUserInfoByName(username);
        if (loginUser == null) {
            return Result.error(MessageConstant.USERNAME_ERROR);
        }
        // 判断密码正确性
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 登录成功，返回JWT令牌
            Map <String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error(MessageConstant.PASSWORD_ERROR);
    }

}
