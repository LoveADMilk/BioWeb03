package com.bio.common.util;

import java.util.UUID;

/**
 * @description: ID管理类
 * @author: luohanye
 * @create: 2019-04-19
 **/
public class IDUtils {

    public static String getUUID(){
        String code = UUID.randomUUID().toString().replace("-","");
        return code;

    }

}
