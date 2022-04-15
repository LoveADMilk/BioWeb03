package com.bio.virusInfo.service.Impl;

import com.bio.entityModel.model.virusInfo.VirusHI;
import com.bio.virusInfo.mapper.VirusHIMapper;
import com.bio.virusInfo.service.VirusHIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
