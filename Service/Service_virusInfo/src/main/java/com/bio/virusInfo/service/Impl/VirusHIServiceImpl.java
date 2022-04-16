package com.bio.virusInfo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.entityModel.model.virusInfo.VirusHI;
import com.bio.virusInfo.mapper.VirusHIMapper;
import com.bio.virusInfo.service.VirusHIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VirusHIServiceImpl implements VirusHIService {
    @Autowired
    private VirusHIMapper virusHIMapper;

    @Override
    public void insertVirusHI(VirusHI virusHI) {
        virusHIMapper.insert(virusHI);
    }


    /***
     * 计算抗原距离
     *
     * **/
    @Override
    public Double computeDistance(Integer abHI, Integer baHI, Integer aaHI, Integer bbHI) {
        Float ab = Float.valueOf(abHI);
        Float ba = Float.valueOf(baHI);
        Float aa = Float.valueOf(aaHI);
        Float bb = Float.valueOf(bbHI);
        Double res = Math.sqrt((aa * bb)/(ab * ba));
        return res;
    }

    @Override
    public List<VirusHI> selectVirusHIByType(String type) {

        List<VirusHI> res = virusHIMapper.selectByType(type);

        return res;
    }

    @Override
    public Page<VirusHI> selectVirusHIByTypePage(Page<VirusHI> virusHIPage ,String type) {
        QueryWrapper<VirusHI> sectionQueryWrapper = new QueryWrapper<VirusHI>();
        sectionQueryWrapper.eq("type", type);
        Page<VirusHI> page = virusHIMapper.selectPage(virusHIPage, sectionQueryWrapper);
        return page;
    }

    @Override
    public Page<VirusHI> selectVirusHIByNamePage(Page<VirusHI> virusHIPage, String name) {
        QueryWrapper<VirusHI> sectionQueryWrapper = new QueryWrapper<VirusHI>();
//        SELECT * FROM virushi
//        WHERE nameA = "A" OR nameB = "A";
        sectionQueryWrapper
                .eq("nameA", name)
                .or()
                .eq("nameB", name);
        Page<VirusHI> page = virusHIMapper.selectPage(virusHIPage, sectionQueryWrapper);
        return page;
    }
}
