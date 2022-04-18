# BioWeb03

生信平台

# ![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/index.PNG?raw=true)


新增**特殊功能1-序列内容对比（同类型）-->获得变异点**

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