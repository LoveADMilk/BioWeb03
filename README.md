# BioWeb

Virus生信平台

2022/04/24-新增特殊功能3-WebSocket + RabbitMQ 实现异步训练状态回传的功能

2022/04/20-新增特殊功能2-定时任务之论文爬取

2022/04/15-新增特殊功能1-序列内容对比（同类型）-->获得变异点

新增特殊功能0-ASN文件提取PSSM矩阵

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/index.PNG?raw=true)

## 1 asn文件上传返回PSSM文件

目的：无登录状态下完成上传asn文件与下载PSSM文件

**特殊功能0-ASN文件提取PSSM矩阵**

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/asn%E6%96%87%E4%BB%B6%E8%BD%AC%E6%8D%A2%E4%B8%BAPSSM.PNG?raw=true)

具体设计：

[BioWeb03/功能1asn文件上传返回PSSM文件.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能1asn文件上传返回PSSM文件.md)

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/1-2.PNG?raw=true)


## 2 登录功能
使用邮箱注册，并返回验证链接，用户点击链接之后才能实现注册成功，点击链接之前，用户对象信息存入redis中，并设置过期时间

通过在cookie中加入token,使用JWT方案解决保持登录状态

具体设计

[BioWeb03/功能2-登录功能.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能2-登录功能.md)

2022/5/3-完善JWTHelper,针对JWT过期一场进行捕捉处理

## 3 流感数据信息

主要功能：增删改查、分页显示

**特殊功能1-序列内容对比（同类型）-->获得变异点**

具体设计：

[BioWeb03/功能3-流感数据信息.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能3-流感数据信息.md)


## 4 流感相关论文模块

可以显示前沿论文的相关信息

**特殊功能2-定时任务之论文爬取**

具体设计：

[BioWeb03/功能4-流感相关论文模块.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能4-流感相关论文模块.md)


![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/4-1.PNG?raw=true)

  爬虫具体设计与代码：

  [BioWeb-Flask/ExtractPaper.py at master · LoveADMilk/BioWeb-Flask (github.com)](https://github.com/LoveADMilk/BioWeb-Flask/blob/master/ExtractPaper.py)

简介-`@Scheduled`做循环定时任务，访问Flask端口，利用python的beauty soup做爬虫，然后存入数据库


## 5 深度学习模型部署

部署深度学习模型，提供预测结果与状态回传

详细设计图：

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/5design.PNG?raw=true)

**特殊功能3-WebSocket + RabbitMQ 实现异步训练状态回传的功能**

[BioWeb03/功能5-深度学习模型部署.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能5-深度学习模型部署.md)


## 6数据可视化分析

通过VUE与ANTV实现疫情地图，与病毒抗原关系图

病毒世界地图：

可以通过点击具体点位获得菌株的具体信息

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/6-1.PNG?raw=true)

举例，H3N2病毒抗原关系图:

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/H3N2.PNG?raw=true)


处理数据获得JSON数据代码：

https://github.com/LoveADMilk/BioWeb03/blob/master/summary/code/CreateH3N2Node.py


## 微服务相关

开启Nacos与gateWay，使用其路由与断言功能

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/Nacos.PNG?raw=true)

