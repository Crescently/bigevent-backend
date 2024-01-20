package com.bigeventbackend.service;

import com.bigeventbackend.pojo.entity.User;

public interface UserService {

    User getUserInfoByName(String username);

    void register(String username, String password);

    // 更新
    void updateUserInfo(User user);

    void updateAvatar(String avatarUrl);

    void updatePassword(String newPassword);
}
