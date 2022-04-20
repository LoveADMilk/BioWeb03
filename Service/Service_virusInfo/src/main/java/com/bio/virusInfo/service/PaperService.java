package com.bio.virusInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusPaper.Paper;

public interface PaperService {
    Page<Paper> selectPaperByPage(Page<Paper> paperPage);

    Page<Paper> selectPaperByTimePage(Page<Paper> paperPage, String time);

    Page<Paper> selectPaperByCitationsPage(Page<Paper> paperPage, String order);

    Page<Paper> selectPaperByTimeCitationsPage(Page<Paper> paperPage, String time, String order);
}
