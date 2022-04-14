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

## 2 登录功能
使用邮箱注册，并返回验证链接，用户点击链接之后才能实现注册成功，点击链接之前，用户对象信息存入redis中，并设置过期时间

验证功能：

1 先再QQ邮箱开启POP3和SMTP服务

2 核心逻辑代码

```java
@GetMapping("/postMali")
public String senMail(){
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setSubject("你好");
    simpleMailMessage.setText("这是一封测试文件");
    simpleMailMessage.setTo("yj7558921@163.com");//目标网址
    simpleMailMessage.setFrom("742854584@qq.com");//
    System.out.println("发送了邮件");

    javaMailSender.send(simpleMailMessage);//发送邮件
    System.out.println("---------");
    return "OK";

```

3 延时注入数据库，先将用户信息存入到Redis中，并设置过期时间，过期时间内点击验证链接，方可完成注册存入数据库之中

```java
redisTemplate.opsForValue().set(activeCode, user, 60*10, TimeUnit.SECONDS);//设这10分钟的过期时间
```

```java
redisTemplate.hasKey(code)//判断是否存在
```



注意点1-由于使用的是主键自增策略，所以在实体类的id中加入注解@TableId(value = “id”,type = IdType.AUTO)//主键生成策略

注意点2-MySQL表中的字段尽量用驼峰命名，如果采用下划线命名可能会导致出现，返回值为null的情况，其原因在于Mybatis无法识别

注意点3-序列化implements Serializable，当我们想把实体类对象序列化到redis中的时候需要对实体类添加implements Serializable，允许其可以序列化

​		否则会报错`DefaultSerializer requires a Serializable payload but received an object of type`

注意点4-想要获取RedisTemplate< String, Object>的Bean，要根据名字装配。也就是使用@Resource


## 3 录入流感数据信息

### 3.1 数据表：

（1）流感数据基本信息表

| 流感ID | 流感名           | 地点   | 城市经纬度 | 时间     | 季节       | create时间 | 修改时间 | 流感类型         | 序列（text)                                                  | 备注 | 上传用户ID | 数据来源           | 序列类型           |
| ------ | ---------------- | ------ | ---------- | -------- | ---------- | ---------- | -------- | ---------------- | ------------------------------------------------------------ | ---- | ---------- | ------------------ | ------------------ |
| 1      | A/BAYERN/69/2009 | BAYERN | ***        | 2009-1-1 | 0、1、2、3 | 时间戳     | 时间戳   | H1N1、H3N2、H5N1 | mdvnptllfl kvpaqnaist tfpytgdppy shgtgtgytm dtvnrthqys ergrwtknte | ***  | 1          | 实验室名字、期刊等 | HA、 PB1、完整序列 |

```sql
CREATE TABLE `virus` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userId` BIGINT(20) NOT NULL COMMENT '用户编号',
  `name` VARCHAR(100) NOT NULL COMMENT '流感名称',
  `address` VARCHAR(100) DEFAULT NULL COMMENT '所在城市',
  `longitude` DECIMAL(9,6) DEFAULT NULL COMMENT '经度',
  `latitude` DECIMAL(9,6) DEFAULT NULL COMMENT '纬度',
  `time` DATE DEFAULT NULL COMMENT '定义时间',
  `season` TINYINT DEFAULT NULL COMMENT '季节0、1、2、3春夏秋冬',
  `type` VARCHAR(100) DEFAULT NULL COMMENT '流感类型(H1N1\H3N1\H5N1...)',
  `sequence` TEXT DEFAULT NULL COMMENT '流感序列内容',
  `sequenceType` VARCHAR(100) DEFAULT NULL COMMENT '流感序列类型(HA\HB\HA1\HA2\null...)',
  `dataFrom` VARCHAR(100) DEFAULT NULL COMMENT '数据来源',
  `tip` TEXT DEFAULT NULL COMMENT '备注内容',
  `createTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流感数据表';
```
![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/3-1.PNG?raw=true)


经纬度选择上使用

1.
longitudes（经度）: decimal(9,6)
2. latitudes （纬度）: decimal(8,6)

经度取值范围为`[0,180]`，纬度的取值范围为`[0,90]`

float,double容易产生误差，对精确度要求比较高时，建议使用decimal来存，decimal在mysql内存是以字符串存储的

城市经纬度表（考虑）查询城市的经纬度

（2）患者个体信息表

​			记录不同流感患者的个体的患病体征与处方数据

| 患者ID | 流感名           | 流感ID | 患病时间 | 症状    | create时间 | 修改时间 | 处方（text) | 备注 | 上传用户ID |
| ------ | ---------------- | ------ | -------- | ------- | ---------- | -------- | ----------- | ---- | ---------- |
| 1001   | A/BAYERN/69/2009 | BAYERN | 2009-1-1 | ***text | 时间戳     | 时间戳   | ***text     | ***  | 1          |



（3）流感论文表（用户可以自行上传自己的论文，或者admin用户上传经典论文）

​			收集不同论文用于归纳流感病毒研究的整个进程

| 论文ID | 所属流感类别 | 论文名字 | 地址       | 期刊 | 作者 | create时间 | 修改时间 | 备注 | 上传用户ID |
| ------ | ------------ | -------- | ---------- | ---- | ---- | ---------- | -------- | ---- | ---------- |
| 1001   | H1N1、H3N1   | ...      | http://... |      |      | 时间戳     | 时间戳   | ***  | 1          |

（4）流感滴定数据表与流感抗原距离表

​			用于神经网络与机器学习应用

| ID   | 流感A名          | 流感B名 | 流感A的ID | A对B的滴定值 | A对B的滴定值 | A对A的滴定值 | B对B的滴定值 | 抗原距离 | 备注 | 上传用户ID | 创建时间 | 修改时间 |
| ---- | ---------------- | ------- | --------- | ------------ | ------------ | ------------ | ------------ | -------- | ---- | ---------- | -------- | -------- |
| 1    | A/BAYERN/69/2009 | BAYERN  | 2009-1-1  |              |              |              |              |          | ***  | 1          |          |          |



注意1-input类型为date时，传递到后端的值类型是String类型

```html
<td><input type="date" id="time" name="time"/></td>
```

后端也是直接传就行,	格式为`2022-04-01`

### 3.2 功能

特殊功能1-序列内容对比（同类型）-->获得变异点；
