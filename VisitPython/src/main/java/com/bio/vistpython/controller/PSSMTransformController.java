package com.bio.vistpython.controller;

import com.bio.vistpython.httpClient.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller //如果是RestController那就会找不到目标网页
public class PSSMTransformController {

    @GetMapping("/uploadPSSM")
    public String UploadPSSM(){
        return "upload";
    }

    @Autowired
    HttpClient httpClient;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;//自动注入的时候一定要加入泛型

    //多文件上传功能
    //文件上传之后需要先判断其后缀是否是asn
    //多文件上传之后存在三种可能
    //1.该文件上传成功
    //2.该文件上传失败，也就是上传文件出现异常
    //3.该input是空，也就是没有文件上传

    //当上传完文件之后需要通知flask接口去提取asn文件到PSSM文件中
    //方案1.将文件地址通过HTTP参数传过去（考虑到后续需要将Flask集成到微服务模块中，故先不做这个方案）
    //方案2.通过告知flask，已经将文件地址传入Redis中了，让Flask在Redis中寻找

    //通知就用RabbitMQ来做，监听者在Java这边，生产者在Flask，从Redis中提取文件地址（跨语言使用RabbitMQ实现异步通知文件下载功能）
    @PostMapping("/uploadPSSMBatch")
    //@ResponseBody  不能与Thymeleaf 一起使用，否则无法返回网页
    public String uploadPSSMBatch(MultipartHttpServletRequest request, Model model){
        Map<String, String> resultMap = new HashMap<>();//前端返回一个hashmap,告知前端哪些上传成功哪些失败了
        List<MultipartFile> files = request.getFiles("file");
        List<String> fileAddressList = new ArrayList<>();//存放上传成功的文件地址
        String filePath = "E:\\uploadPSSM\\";//文件上传的地址
        //Rediskey nowTime + filename,value用list【path1,path2...】
        String nowTime = System.currentTimeMillis() +"";
        String keyFileName = nowTime + "fileName";
        for (int i = 0; i < files.size(); i++){
            MultipartFile file = files.get(i);

            if(file.getOriginalFilename().equals("")) continue;//如果上传位置空置放，就将其跳过
            //判断当前文件后缀
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            log.info("当前文件的后缀为： " + type);
            if (!type.equals(".asn")){
                resultMap.put(file.getOriginalFilename(), ": 文件后缀名错误，请重新上传");
                continue;
            }
            if (!file.isEmpty()){
                String path = uploadFile(file, filePath);//path是文件的绝对路径地址
                if(path == null){
                    resultMap.put(file.getOriginalFilename(), ": 文件上传失败，请重新上传");
                    log.info("第 "+i+" 个文件上传失败");
                }else{
                    resultMap.put(file.getOriginalFilename(), ": 文件上传成功");
                    log.info("第 "+i+" 个文件上传成功");
                    fileAddressList.add(path);
                }
            }else {
                log.info("第 "+i+" 个文件未上传");
            }
        }
        //将上传成功的文件绝对地址传入到Redis中
        //Redis部分
        //Redis的key为 nowTime + filename,value用list【path1,path2...】
        ListOperations<String, String> listOps = redisTemplate.opsForList();
        listOps.leftPushAll(keyFileName, fileAddressList);//存入Redis

        Long len = redisTemplate.opsForList().size(keyFileName);
        log.info("该批次的文件在Redis的Key是： " + keyFileName);
        log.info("文件列表长度为： "+ redisTemplate.opsForList().size(keyFileName));
        log.info(String.valueOf("完整列表为"+redisTemplate.opsForList().range(keyFileName, 0, len)));

        //利用RestTemplate实现调用并且传入该批次的Redis的Key值
        String url = "http://127.0.0.1:5000" + "/" + keyFileName + "/";//Flask启动的默认端口

        log.info("访问Flask的网址为" + url);
        HttpMethod httpMethod = HttpMethod.GET;//Flask 在开启端口的时候默认是get访问
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        httpClient.client(url, httpMethod, params);

        model.addAttribute("resultMap",resultMap);

        return "UploadSuccessed";
        //加入key时出现乱码--》解决办法：配置类新加入序列化
    }


    /**
     * 文件上传工具类
     * @param file
     * @param filePath
     * @return
     */
    private String uploadFile(MultipartFile file, String filePath){
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取当前时间，拼接到文件名中，避免文件名重复
        String today = System.currentTimeMillis() +"";

        File newFile = new File(filePath, today + fileName);
        // 检测是否存在该目录
        if (!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(newFile);
            return filePath+today+fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
