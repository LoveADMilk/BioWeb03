package com.bio.virusInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.entityModel.model.virusInfo.VirusHI;

import java.util.List;

public interface VirusHIService {
    void insertVirusHI(VirusHI virusHI);

    //用于计算抗原距离
    Double computeDistance(Integer abHI, Integer baHI, Integer aaHI, Integer bbHI);

    //返回所有符合条件的结果，并存入列表
    List<VirusHI> selectVirusHIByType(String type);

    Page<VirusHI> selectVirusHIByTypePage(Page<VirusHI> virusHIPage ,String type);

    Page<VirusHI> selectVirusHIByNamePage(Page<VirusHI> virusHIPage, String name);
}
