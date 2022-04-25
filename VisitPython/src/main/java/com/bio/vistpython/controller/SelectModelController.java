package com.bio.vistpython.controller;

import com.bio.entityModel.model.virusModel.VirusModel;
import com.bio.vistpython.service.SelectModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

    //接收传递进来的modelId,传入前端然后
    //选择合适的编码方式然后输入待预测的序列两条
    //点击提交预测之后，传递到/getResults
    //其中 传入 modelId、EncodeId、然后菌株的序列传入到Redis中

    @RequestMapping("/selectEncodeAndInputHtml/{modelId}/{modelName}")
    public String selectEncodeAndInput(
            Model model,
            @PathVariable(value = "modelId") Integer modelId,
            @PathVariable(value = "modelName") String modelName){

        model.addAttribute("modelId", modelId);
        model.addAttribute("modelName", modelName);

        return "selectEncodeAndInput";
    }

}
