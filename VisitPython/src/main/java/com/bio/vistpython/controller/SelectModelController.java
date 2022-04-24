package com.bio.vistpython.controller;

import com.bio.entityModel.model.virusModel.VirusModel;
import com.bio.vistpython.service.SelectModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SelectModelController {

    @Autowired
    private SelectModelService selectModelService;

    @RequestMapping("/selectForecastHtml")
    public String selectForecastHtml(Model model){
        //从数据库中传入模型的编号名字等信息

        List<VirusModel> modelList = selectModelService.selectAll();

        model.addAttribute("modelList", modelList);

        return "SelectForecast";
    }

}
