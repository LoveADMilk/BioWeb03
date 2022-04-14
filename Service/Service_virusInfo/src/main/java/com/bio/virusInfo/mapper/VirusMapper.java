package com.bio.virusInfo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusInfo.Virus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VirusMapper extends BaseMapper<Virus> {


    void updateTimeById(Virus virus);

    void insertVirus(Virus virus);

}
