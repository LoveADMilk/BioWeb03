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
     *  查询用户
     * @param user
     * @return
     */
    userInfo selectUserInfo(userInfo user);


}
