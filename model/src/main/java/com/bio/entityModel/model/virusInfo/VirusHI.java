package com.bio.entityModel.model.virusInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("virusHI")
public class VirusHI {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @TableField("nameA")
    private String nameA;

    @TableField("nameB")
    private String nameB;


    @TableField("abHI")
    private Integer abHI;

    @TableField("baHI")
    private Integer baHI;

    @TableField("aaHI")
    private Integer aaHI;

    @TableField("bbHI")
    private Integer bbHI;

    @TableField("distance")
    private Double distance;

    //流感类型(H1N1\H3N1\H5N1...)
    @TableField("type")
    private String type;

    @TableField("tip")
    private String tip;
}
