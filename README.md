# BioWeb

Virus生信平台

2022/04/24-新增特殊功能3-WebSocket + RabbitMQ 实现异步训练状态回传的功能

2022/04/20-新增特殊功能2-定时任务之论文爬取

2022/04/15-新增特殊功能1-序列内容对比（同类型）-->获得变异点

新增特殊功能0-ASN文件提取PSSM矩阵

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/index.PNG?raw=true)

## 1 asn文件上传返回PSSM文件

目的：无登录状态下完成上传asn文件与下载PSSM文件

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/asn%E6%96%87%E4%BB%B6%E8%BD%AC%E6%8D%A2%E4%B8%BAPSSM.PNG?raw=true)

具体设计：

[BioWeb03/功能1asn文件上传返回PSSM文件.md at master · LoveADMilk/BioWeb03 (github.com)](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/功能1asn文件上传返回PSSM文件.md)

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/1-2.PNG?raw=true)


## 2 登录功能
使用邮箱注册，并返回验证链接，用户点击链接之后才能实现注册成功，点击链接之前，用户对象信息存入redis中，并设置过期时间

通过在cookie中加入token,使用JWT方案解决保持登录状态

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



![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/4-1.PNG?raw=true)

  爬虫具体设计与代码：

  [BioWeb-Flask/ExtractPaper.py at master · LoveADMilk/BioWeb-Flask (github.com)](https://github.com/LoveADMilk/BioWeb-Flask/blob/master/ExtractPaper.py)

简介-`@Scheduled`做循环定时任务，访问Flask端口，利用python的beauty soup做爬虫，然后存入数据库


## 5 深度学习模型部署

部署深度学习模型，提供预测结果与状态回传

### 5-1 WebSocket + RabbitMQ 实现异步训练状态回传的功能

由于用户下需要上传毒株的HA段，深度学习需要转换为矩阵然后投入到模型进行预测，这就导致时间上有延迟，所以需要通知用户进度。

1. Ajax用做进度条的话，是通过轮询的方式不断地访问端口地址，开销大

2. 开启webSocket这样只需要占住一个HTTP链接

消息回传的方式

​	1. 使用JS的定时函数，每隔N秒进行访问Redis获取状态

​			问题在于访问Redis增加开销，不如主动发送信息

​	2. WebSocket + RabbitMQ 实现训练状态获取功能--》显示到前端（最优）

- 前置问题：用户的跨域问题
  - 采用JWT方式，将token存入cookie中

- 主要问题1：如何保证回传到指定用户（引入RabbitMQ如何保证唯一性而不会群发呢？

  - 通过异步访问带入唯一的用户ID作为消息传递的媒介，将ID作为Key与WebSocket的对象作为value存入ConcurrentHashMap中,这样消息回传时，从找到匹配ID即可
- 主要问题2：用RestTemplate访问时发生了阻塞，只能接收全部的消息后，才能跳转到页面
  - AsyncRestTemplate解决

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/5-1.PNG?raw=true)


详细设计图：



其次是否需要心跳机制保持链接呢？