package com.bio.userInfo.service;

import com.bio.entityModel.model.user.userInfo;

public interface UserService {
    /**
     *  用户注册
     * @param user
     */
//    void add(userInfo user);

    void insertUser(userInfo user);

    Integer selectUserIdByMail(String email);
}
