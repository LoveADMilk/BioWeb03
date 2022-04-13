package com.bio.entityModel.model.virusInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("virus")
public class Virus {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("name")
    private String name;

    @TableField("address")
    private String address;

    //精度
    @TableField("longitude")
    private BigDecimal longitude;

    //纬度
    @TableField("latitude")
    private BigDecimal latitude;

    @TableField("time")
    private String time;

    //    季节0、1、2、3春夏秋冬
    @TableField("season")
    private Integer season;

    //流感类型(H1N1\H3N1\H5N1...)
    @TableField("type")
    private String type;

    @TableField("sequence")
    private String sequence;

    @TableField("sequenceType")
    private String sequenceType;

    @TableField("dataFrom")
    private String dataFrom;

    @TableField("tip")
    private String tip;



}

