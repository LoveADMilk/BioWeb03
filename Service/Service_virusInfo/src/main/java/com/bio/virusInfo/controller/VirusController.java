package com.bio.virusInfo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.entityModel.model.virusInfo.VirusHI;
import com.bio.virusInfo.mapper.VirusHIMapper;
import com.bio.virusInfo.mapper.VirusMapper;
import com.bio.virusInfo.service.VirusHIService;
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
    private VirusHIService virusHIService;

    @Autowired
    private VirusMapper virusMapper;

    /**
     * 此部分内容用于跳转到相关页面
     * **/
    @RequestMapping("/")
    public String index(){
        return "myIndex";
    }

    @RequestMapping("/uploadVirusHtml")//进入上传病毒信息页面
    public String uploadVirusHtml(){
        return "uploadVirus";
    }
    @RequestMapping("/analyseVirusSequenceHtml")//进入病毒序列比对页面
    public String analyseVirusSequenceHtml(){
        return "analyseVirusSequence";
    }
    @RequestMapping("/uploadVirusHIHtml")//进入病毒序列比对页面
    public String uploadVirusHIHtml(){
        return "uploadVirusHI";
    }
    @RequestMapping("/virusHIInfoSelectHtml")//进入选择以什么方式获得hi数据，by type, 还是 by name
    public String virusHIInfoSelectHtml(){
        return "virusHIInfoSelect";
    }

    /////////////////////////////
    //    显示virus主要信息，然后用户点进去之后显示完整信息
//      分页显示
    @RequestMapping("/list")
    public String searchAll(Model model,
                            @RequestParam(value = "pn", defaultValue = "1")Integer pn){
//        Page<Virus> virusPage = new Page<>(2,5);
        System.out.println("index"+pn);
        Page<Virus> virusPage = new Page<>(pn, 10,true);
        Page<Virus> page = virusMapper.selectPage(virusPage, null);
        model.addAttribute("page", page);
        return "virusInfoPage";

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
        return "uploadVirus";
    }

    /**
     *
     * @// TODO: 2022/4/17  下一部分就是从前端接受两个菌株的名字、或者是菌株的序列、再或者是选择框选择2个菌株然后提交查看
     * 传入三个数组 1个长度值，然后判断如果是-1，那就显示红色
     *
     * **/

    /**
     *
     * 输入两个菌株的名字查看对比
     * **/
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

    /****
     * 流感滴定数据表与抗原距离表的上传与展示
     * 用于机器学习与深度学习
     * 抗原距离的计算方式有两种，带扩展：若自我滴定为空，就选用方式2
     *
     * ***/
    @RequestMapping("/virusHIUpload")
    public String virusHIUpload(VirusHI virusHI){
        virusHI.setUserId(1);
        Double distance = virusHIService.computeDistance(virusHI.getAbHI(), virusHI.getBaHI(),
                virusHI.getAaHI(), virusHI.getBbHI());
        virusHI.setDistance(distance);
        virusHIService.insertVirusHI(virusHI);
        return "uploadVirusHI";
    }


    //查看HI详情by virus的Type，由于需要所有的数据应用训练,所以返回所有的滴度值（按病毒类型），也可以指定名字查询， 分页显示
    //可以下载所有数据的文件 Model model,
    //                            @RequestParam(value = "type") String type
    /**
     * 根据病毒类型分页显示所有hi滴度数据
     * H1N1、H3N2、H5N1、并做到可以下载所有当前符合类型的数据
     * 整个写的不好，最好用name的方式，在路径中传参，具体看paper的controller
     * **/
    @RequestMapping("/virusHIInfoByType")
    public String virusHIInfoByType(Model model,
                              @RequestParam(value = "pn", defaultValue = "1")Integer pn,
                              @RequestParam(value = "requestType",required = false)String requestType,
                              @ModelAttribute(value = "type") String type){
        Page<VirusHI> virusHIPage = new Page<>(pn, 10,true);
        String usefulType;
        if(type.equals("")){
            usefulType = requestType;
        }else{
            usefulType = type;
        }
        System.out.println(usefulType);
        Page<VirusHI> page = virusHIService.selectVirusHIByTypePage(virusHIPage,usefulType);

        if (type.equals("")){
            model.addAttribute("currentType", requestType); //后续都通过路径传入type
        }else{
            model.addAttribute("currentType", type); //将当前的类型传回去，方便下一页使用
        }
        model.addAttribute("page", page);

        System.out.println(page.getRecords());
        return "virusHIPageByType";
    }
    /**
     * 根据病毒名字，由于病毒可能是A也可能是B所以找到所有满足条件的
     * 分页显示所有hi滴度数据
     ***/
    @RequestMapping("/virusHIInfoByName")
    public String virusHIInfoByName(Model model,
                              @RequestParam(value = "pn", defaultValue = "1")Integer pn,
                              @RequestParam(value = "name",required = false)String name){
        Page<VirusHI> virusHIPage = new Page<>(pn, 10,true);
        Page<VirusHI> page = virusHIService.selectVirusHIByNamePage(virusHIPage, name);
        model.addAttribute("page", page);
        model.addAttribute("name", name);
        System.out.println(page.getRecords());
        return "virusHIPageByName";
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

}
