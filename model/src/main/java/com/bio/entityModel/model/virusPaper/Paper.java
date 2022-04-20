package com.bio.entityModel.model.virusPaper;

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
@TableName("paper")
public class Paper {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //文章名称
    @TableField("name")
    private String name;

    @TableField("author")
    private String author;

    @TableField("address")
    private String address;

    @TableField("info")
    private String info;

    @TableField("year")
    private String year;

    @TableField(value = "citations")
    private Integer citations;

    @TableField("pdf")
    private String pdf;

}
