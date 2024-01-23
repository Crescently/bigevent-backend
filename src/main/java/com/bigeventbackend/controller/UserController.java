package com.bigeventbackend.controller;

import com.bigeventbackend.common.Result;
import com.bigeventbackend.constant.MessageConstant;
import com.bigeventbackend.pojo.entity.User;
import com.bigeventbackend.service.UserService;
import com.bigeventbackend.utils.JwtUtil;
import com.bigeventbackend.utils.Md5Util;
import com.bigeventbackend.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated // 参数校验工具
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/register")
    public Result userRegister(@Pattern(regexp = "^\\S{5,16}") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
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

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/login")
    public Result userLogin(@Pattern(regexp = "^\\S{5,16}") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
        // 1.根据用户名查询用户
        User loginUser = userService.getUserInfoByName(username);
        if (loginUser == null) {
            return Result.error(MessageConstant.USERNAME_ERROR);
        }
        // 判断密码正确性
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())) {
            // 登录成功，返回JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            // 把token存入redis
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error(MessageConstant.PASSWORD_ERROR);
    }

    @GetMapping("/userInfo")
    public Result<User> getUserInfo() {

        // 1.解析token获取用户名
//        Map<String, Object> map = JwtUtil.parseToken(token);
//        String username = (String) map.get("username");

        // 使用ThreadLocal获取用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        // 2.查询数据库
        User userInfo = userService.getUserInfoByName(username);

        return Result.success(userInfo);
    }

    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody @Validated User user) {
        userService.updateUserInfo(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePassword(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        // 1.校验参数
        String oldPassword = params.get("old_pwd");
        String newPassword = params.get("new_pwd");
        String rePassword = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(rePassword)) {
            return Result.error(MessageConstant.PARAM_ERROR);
        }
        // 原密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.getUserInfoByName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPassword))) {
            return Result.error(MessageConstant.OLD_PWD_ERROR);
        }
        if (!newPassword.equals(rePassword)) {
            return Result.error(MessageConstant.TWO_PWD_NOT_MATCH);
        }
        userService.updatePassword(newPassword);
        // 删除redis中的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }


}


