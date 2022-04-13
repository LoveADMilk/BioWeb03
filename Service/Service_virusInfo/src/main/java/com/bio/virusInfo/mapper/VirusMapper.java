package com.bio.virusInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bio.entityModel.model.virusInfo.Virus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VirusMapper extends BaseMapper<Virus> {

    Virus searchAll(int i);
}
