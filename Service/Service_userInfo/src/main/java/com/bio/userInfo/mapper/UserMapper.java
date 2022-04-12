package com.bio.userInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bio.entityModel.model.user.userInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<userInfo> {

    /**
     *  注册
     * @param user
     */
    void insertUserInfo(userInfo user);

    /**
     *  根据激活码查询用户
     * @param activeCode
     * @return
     */

    userInfo selectUserInfoByActiveCode(String activeCode);

    /**
     *  更新用户
     * @param user
     */

    void updateUserInfo(userInfo user);



    /**
     *  根据mail查询用户是否存在
     * @param email
     * @return
     */
    Integer selectUserIdByMail(String email);

    userInfo selectUserByMail(String email);
}
