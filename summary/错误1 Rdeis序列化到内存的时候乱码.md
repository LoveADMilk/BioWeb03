错误1 Rdeis序列化到内存的时候乱码

利用Java将Key-Value 传入到Redis中后，python使用key调用发现不存在

于是到Redis中寻找keys *;

发现所有的key 全是乱码状态
![img](https://github.com/LoveADMilk/BioWeb03/blob/master/summary/image/mistake1.PNG?raw=true "img1")

于是 判断 Redis中的序列化设置出现了问题

首先是发现idea爆红发现RedisConnectionFactory 并没有导入到容器

于是全局类@Resource导入

结果，没有解决问题

于是上网查找，发现可能是注入RedisTemplate的时候没加入泛型

```
@Autowired
private RedisTemplate<String , String> redisTemplate;//自动注入的时候一定要加入泛型
```

OK，解决问题