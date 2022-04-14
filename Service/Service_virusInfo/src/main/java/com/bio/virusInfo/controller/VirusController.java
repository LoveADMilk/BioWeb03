package com.bio.virusInfo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.virusInfo.mapper.VirusMapper;
import com.bio.virusInfo.service.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;


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
    @RequestMapping("/virusUpload")
    public String virusUpload(Virus virus){
        virus.setUserId(1);//后续扩展JWT验证
        virus.setLongitude(new BigDecimal("1.1"));//经度，后续查城市的经纬度表进行查询
        virus.setLatitude(new BigDecimal("1.1"));//纬度，后续查城市的经纬度表进行查询
        System.out.println(virus);
        virusService.insertVirus(virus);
        return "index";
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
