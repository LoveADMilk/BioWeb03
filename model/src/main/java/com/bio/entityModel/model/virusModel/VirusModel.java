package com.bio.entityModel.model.virusModel;

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
@TableName("model")
public class VirusModel {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //模型名字
    @TableField("name")
    private String name;

    @TableField("info")
    private String info;

    @TableField(value = "modellike")
    private Integer modellike;

    @TableField(value = "modelviews")
    private Integer modelviews;
}
