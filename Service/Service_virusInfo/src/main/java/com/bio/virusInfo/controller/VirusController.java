package com.bio.virusInfo.controller;

import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.virusInfo.mapper.VirusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VirusController {
    @Autowired
    VirusMapper virusMapper;
    @RequestMapping("/searchAll")
    public String searchAll(){
        Virus virus = virusMapper.searchAll(1);
        System.out.println(virus);
        return "OK";
    }
}
