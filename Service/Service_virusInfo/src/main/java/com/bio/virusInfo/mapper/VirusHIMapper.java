package com.bio.virusInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bio.entityModel.model.virusInfo.VirusHI;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VirusHIMapper extends BaseMapper<VirusHI> {

    List<VirusHI> selectByType(String type);
}
