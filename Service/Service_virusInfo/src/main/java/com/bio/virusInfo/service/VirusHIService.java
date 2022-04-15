package com.bio.virusInfo.service;

import com.bio.entityModel.model.virusInfo.VirusHI;

public interface VirusHIService {
    void insertVirusHI(VirusHI virusHI);

    //用于计算抗原距离
    Double computeDistance(Integer abHI, Integer baHI, Integer aaHI, Integer bbHI);
}
