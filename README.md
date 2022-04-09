# BioWeb03

生信平台

## 1 asn文件上传返回PSSM文件

目的：无登录状态下完成上传asn文件与下载PSSM文件

![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/asn%E6%96%87%E4%BB%B6%E8%BD%AC%E6%8D%A2%E4%B8%BAPSSM.PNG?raw=true)

### 1.1 asn上传部分

**确定唯一地址前缀**

随后每批次上传，生成1个唯一名（用时间拼接）的文件夹，然后在此文件夹下上传本次文件

![图1.1](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/%E5%9B%BE1.1.PNG?raw=true)

过程描述，上传主要使用到了Redis、RabbitMQ
 
- Redis主要存放本次上传中的所有文件的上传位置（全路径地址），key为文件名，value是list,包含具体文件的全地址

- 上传文件完毕之后，使用RestTemplate访问，Flask的端口，传递参数为key，这样可以利用python语言获取所有已上传文件的路径，方便转换
- 进行转换
- 转换完毕之后，将生成pssm结尾的文件，并存入相同路径之下
- 每转换完毕一个文件，就利用Rabbit MQ传递一个消息到默认交换机（未改），消息内容为已经转换后的文件名
- 消息队列的监听端输出接受到的信息

### 1.2 Flask端的文件提取转换部分
**具体代码：**

https://github.com/LoveADMilk/BioWeb-Flask

文件提取部分通过PSSMConvert类进行，对asn文件的提取Pssm矩阵信息

### 1.3 PSSM下载部分

方案1：不做消息传向前端的处理，直接跳转后下载

服务器map中存入每一个文件上传后的部分地址，传入前端显示已经上传，然后点击对应文件下载之后，将地址前缀再回传到服务器，并与当前地址拼接然后访问磁盘下载

由于pssm文件与asn文件只有后缀不同，所以只需要更改后缀之后进行下载即可。

方案2： 使用RabbitMQ 与webSocket即可完成双工通信通知下载

没做出来


