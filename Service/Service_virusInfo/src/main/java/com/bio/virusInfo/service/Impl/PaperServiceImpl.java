package com.bio.virusInfo.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusPaper.Paper;
import com.bio.virusInfo.mapper.PaperMapper;
import com.bio.virusInfo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;

    @Override
    public Page<Paper> selectPaperByPage(Page<Paper> paperPage) {
        return paperMapper.selectPage(paperPage, null);
    }

    @Override
    public Page<Paper> selectPaperByTimePage(Page<Paper> paperPage, String time) {
        //按照时间条件where判断
        QueryWrapper<Paper> sectionQueryWrapper = new QueryWrapper<Paper>();
        sectionQueryWrapper.eq("year", time);
        return paperMapper.selectPage(paperPage, sectionQueryWrapper);
    }

    //order by citations
    @Override
    public Page<Paper> selectPaperByCitationsPage(Page<Paper> paperPage, String order) {
        QueryWrapper<Paper> sectionQueryWrapper = new QueryWrapper<Paper>();
        //正序 递增
        if(order.equals("positive")){
            sectionQueryWrapper.orderByAsc("citations");
        }else{
            //倒叙 递减
            sectionQueryWrapper.orderByDesc("citations");
        }
        return paperMapper.selectPage(paperPage, sectionQueryWrapper);
    }
    //按照时间条件where判断, order by citations
    @Override
    public Page<Paper> selectPaperByTimeCitationsPage(Page<Paper> paperPage, String time, String order) {
        QueryWrapper<Paper> sectionQueryWrapper = new QueryWrapper<Paper>();
        //正序 递增
        if(order.equals("positive")){
            sectionQueryWrapper.eq("year", time).orderByAsc("citations");
        }else{
            //倒叙 递减
            sectionQueryWrapper.eq("year", time).orderByDesc("citations");
        }
        return paperMapper.selectPage(paperPage, sectionQueryWrapper);
    }
}
