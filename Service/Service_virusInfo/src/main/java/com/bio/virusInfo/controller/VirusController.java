package com.bio.virusInfo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.virusInfo.mapper.VirusMapper;
import com.bio.virusInfo.service.VirusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class VirusController {
    @Autowired
    private VirusService virusService;


    @Autowired
    private VirusMapper virusMapper;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    //    显示主要信息，然后用户点进去之后显示完整信息
//      分页显示
    @RequestMapping("/list")
    public String searchAll(Model model,
                            @RequestParam(value = "pn", defaultValue = "1")Integer pn){
//        Page<Virus> virusPage = new Page<>(2,5);
        Page<Virus> virusPage = new Page<>(pn, 10,true);
        Page<Virus> page = virusMapper.selectPage(virusPage, null);
        System.out.println(virusPage == page);
        System.out.println(JSON.toJSONString(page));
        System.out.println(page.getRecords().size());
        System.out.println(page.getCurrent());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        model.addAttribute("page", page);
        return "pageIndex";

    }
    //查看详情by virus的Id
    @RequestMapping("/virusInfo")
    public String virusInfo(Model model,
            @RequestParam(value = "virusId") Integer virusId){
        Virus virus = virusService.selectVirusInfoById(virusId);
        System.out.println(virus);
        model.addAttribute("virus", virus);
        return "virusDetail";
    }
    /**
     * 上传Virus
     * **/
    @RequestMapping("/virusUpload")
    public String virusUpload(Virus virus){
        virus.setUserId(1);//后续扩展JWT验证
        virus.setLongitude(new BigDecimal("1.1"));//经度，后续查城市的经纬度表进行查询
        virus.setLatitude(new BigDecimal("1.1"));//纬度，后续查城市的经纬度表进行查询
        System.out.println(virus);
        virusService.insertVirus(virus);
        return "index";
    }

    /**
     *
     * //下一部分就是从前端接受两个菌株的名字、或者是菌株的序列、再或者是选择框选择2个菌株然后提交查看
     *         //传入三个数组 1个长度值，然后判断如果是-1，那就显示红色
     * **/

//  访问输入比对页面的数据前端显示页
    @RequestMapping("compareIndex")
    public String comPareIndex(){
        return "virusCompareRes";
    }
//    上传菌株的序列后对比
    @RequestMapping("/virusCompareBySequence")
    public String virusCompareBySequence(Model model,
                                        //通过前端的name绑定
                                         @ModelAttribute(value = "virusSequence1") String virusSequence1,
                                         @ModelAttribute(value = "virusSequence2") String virusSequence2){
        System.out.println(virusSequence1);
        System.out.println(virusSequence2);
        if(virusSequence1.length() != virusSequence2.length()){
            log.info("两个居住长度、或者类型不同的，不能比较");
        }
        Map<String, Object> res = new HashMap<>();
        res = extractMethod(virusSequence1, virusSequence2);
        for (Map.Entry<String, Object> entry : res.entrySet()){
            model.addAttribute(entry.getKey(), entry.getValue());
        }
        return "compare";
    }
    /**
     * 比对方法封装
     * 比较两个病毒序列，返回不同处的高亮
     * 0 代表缺失 ， 蓝色
     * 1 代表变异点位 ， 红色
     * @param str1,str2
     * 返回hashmap
     * ****/
    private Map<String, Object> extractMethod(String str1, String str2){
        Map<String, Object> map = new HashMap<>();
        int length = str1.length();
        char[] str1Arr = str1.toCharArray();
        char[] str2Arr = str2.toCharArray();
        int arr[] = new int[length];
        for(int i = 0; i < length; i++){
            if(str1Arr[i] != str2Arr[i]){
                arr[i] = 1;
            }else if(str1Arr[i] == '-' || str2Arr[i] == '-'){
                arr[i] = 0;
            }else{
                arr[i] = -1;
            }
        }
        map.put("str1Arr", str1Arr);
        map.put("str2Arr", str2Arr);
        map.put("arr", arr);
        map.put("length", length);
        return map;
    }



//    @RequestMapping("/testuoload")
//    public String testuoload(Virus virus){
//        for (int i = 0; i < 100; i ++){
//            virus.setUserId(1);//后续扩展JWT验证
//            virus.setName("A/BAYERN/69/2009");
//            virus.setAddress("BAYERN");
//            virus.setLongitude(new BigDecimal("1.1"));//经度，后续查城市的经纬度表进行查询
//            virus.setLatitude(new BigDecimal("1.1"));//纬度，后续查城市的经纬度表进行查询
//            virus.setTime("2022-04-01");
//            virus.setSeason(0);
//            virus.setType("H1N1");
//            virus.setSequence("mdvnptllfl");
//            virus.setSequenceType("HA1");
//            virus.setDataFrom("web");
//            virus.setTip("HONGKONG");
//            System.out.println(virus);
//            virusService.insertVirus(virus);
//        }
//        return "idnex";
//    }

}
