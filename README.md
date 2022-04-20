# BioWeb

生信平台

# ![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/index.PNG?raw=true)

- 新增特殊功能2-定时任务之论文爬取

    通过爬虫爬取Google scholar 使得获得**最前沿**相关文献，并存入数据库
  ![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/4-1.PNG?raw=true)
    
    爬虫具体设计与代码：
    
    [BioWeb-Flask/ExtractPaper.py at master · LoveADMilk/BioWeb-Flask (github.com)](https://github.com/LoveADMilk/BioWeb-Flask/blob/master/ExtractPaper.py)

简介-`@Scheduled`做循环定时任务，访问Flask端口，利用python的beauty soup做爬虫，然后存入数据库

- 新增**特殊功能1-序列内容对比（同类型）-->获得变异点**

    前端使用JS+Thymeleaf进行高亮显示变异点：
    
    # ![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/3-4.PNG?raw=true)


## 1 asn文件上传返回PSSM文件

目的：无登录状态下完成上传asn文件与下载PSSM文件

###### ![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/asn%E6%96%87%E4%BB%B6%E8%BD%AC%E6%8D%A2%E4%B8%BAPSSM.PNG?raw=true)

具体设计：

[BioWeb03/功能1asn文件上传返回PSSM文件.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能1asn文件上传返回PSSM文件.md)

## 2 登录功能
使用邮箱注册，并返回验证链接，用户点击链接之后才能实现注册成功，点击链接之前，用户对象信息存入redis中，并设置过期时间

具体设计

[BioWeb03/功能2-登录功能.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能2-登录功能.md)



## 3 流感数据信息

主要功能：增删改查、分页显示、特殊功能高亮对比序列

具体设计：

[BioWeb03/功能3-流感数据信息.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能3-流感数据信息.md)


## 4 流感相关论文模块

具体设计：

[BioWeb03/功能4-流感相关论文模块.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能4-流感相关论文模块.md)

可以显示前沿论文的相关信息

访问路径：`http://localhost:8151/allPaperPage?pn=1&time=2022&order=reverse`

服务端用@RequestParam接受

定时任务爬虫爬取论文数据 -> 存入MySQL中

前端显示页面：

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/4-1.PNG?raw=true)

可以根据时间查询对象年份的论文，并根据引用数递增递减显示

因此主要处理四种情况：

- time & order全为空的情况，就是默认paper表的全部内容
- order不为空 time不空,那就是按照年份条件得到结果，并order by引用数，判断其order内容选择正反序列 positive->ASC递增 reverse->desc递减
- order为空 time不空，那就是按照年份条件得到结果
- order不为空 time空，那就是默认排序下，order by引用数，判断其order内容选择正反序列 positive->ASC递增 reverse->desc递减

分别进行查询操作并返回到前端

sql:

```sql
CREATE TABLE `paper` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` VARCHAR(1000) DEFAULT NULL COMMENT '文章名称',
  `author` VARCHAR(100) DEFAULT NULL COMMENT '作者',
  `address` VARCHAR(100) DEFAULT NULL COMMENT '文章地址',
  `info` VARCHAR(100) DEFAULT NULL COMMENT '所属期刊信息',
  `year` VARCHAR(100) DEFAULT NULL COMMENT '年份',
  `citations` INT(100) DEFAULT NULL COMMENT '引用数',
  `pdf` VARCHAR(100) DEFAULT NULL COMMENT 'pdf地址',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='论文表';
```

