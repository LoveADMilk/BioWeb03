package com.bio.virusInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bio.entityModel.model.virusPaper.Paper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

}
