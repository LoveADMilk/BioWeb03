package com.bio.virusInfo.service.Impl;

import com.bio.entityModel.model.virusInfo.Virus;
import com.bio.virusInfo.mapper.VirusMapper;
import com.bio.virusInfo.service.VirusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VirusServoceImpl implements VirusService {
    @Autowired
    private VirusMapper virusMapper;
    @Override
    public void insertVirus(Virus virus) {
        virusMapper.insertVirus(virus);
        log.info("插入病毒信息成功");
    }

    @Override
    public Virus selectVirusInfoById(Integer virusId) {
        return virusMapper.selectById(virusId);
    }
}
