package com.bio.virusInfo.controller;

import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.virusInfo.mapper.VirusMapper;
import com.bio.virusInfo.service.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class VirusController {
    @Autowired
    private VirusService virusService;

    @RequestMapping("/")
    public String index(){
        return "idnex";
    }
    @RequestMapping("/searchAll")
    public String searchAll(){
//        Virus virus = virusMapper.searchAll(1);
//        System.out.println(virus);
        return "OK";
    }
    @RequestMapping("/virusUpload")
    public String virusUpload(Virus virus){
        virus.setUserId(1);//后续扩展JWT验证
        virus.setLongitude(new BigDecimal("1.1"));//经度，后续查城市的经纬度表进行查询
        virus.setLatitude(new BigDecimal("1.1"));//纬度，后续查城市的经纬度表进行查询
        System.out.println(virus);
        virusService.insertVirus(virus);
        return "idnex";
    }
}
