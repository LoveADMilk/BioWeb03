package com.bio.entityModel.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//必须驼峰命名，否则mybatis的ResultType无法识别下滑线
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("userInfo")
public class userInfo {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("nickName")
    private String nickName;

    @TableField("password")
    private String password;

    @TableField("email")
    private String email;

    // 激活状态 0 未激活 1 已激活
    @TableField("activeStatus")
    private Integer activeStatus;

    // 激活码
    @TableField("activeCode")
    private String activeCode;

}
