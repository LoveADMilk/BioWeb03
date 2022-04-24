package com.bio.vistpython.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bio.entityModel.model.virusModel.VirusModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface SelectModelMapper extends BaseMapper<VirusModel> {

}
