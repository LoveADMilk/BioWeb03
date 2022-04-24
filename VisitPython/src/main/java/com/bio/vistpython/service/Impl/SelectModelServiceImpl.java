package com.bio.vistpython.service.Impl;

import com.bio.entityModel.model.virusModel.VirusModel;
import com.bio.vistpython.mapper.SelectModelMapper;
import com.bio.vistpython.service.SelectModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectModelServiceImpl implements SelectModelService {

    @Autowired
    private SelectModelMapper selectModelMapper;
    @Override
    public List<VirusModel> selectAll() {
        return selectModelMapper.selectList(null);
    }
}
