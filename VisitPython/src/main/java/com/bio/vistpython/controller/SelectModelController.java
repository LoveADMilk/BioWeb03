package com.bio.vistpython.controller;

import com.bio.common.util.GetUserVo;
import com.bio.entityModel.model.virusModel.VirusModel;
import com.bio.vistpython.service.SelectModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class SelectModelController {

    @Autowired
    private SelectModelService selectModelService;

    @RequestMapping("/selectForecastHtml")
    public String selectForecastHtml(Model model,
                                     HttpServletRequest request){
        Map<String, Object> map = GetUserVo.getUserInfo(request);
        if(map.get("Biotoken").equals("") || map.get("userName") == null || map.get("userName").equals("")){
            System.out.println("提醒用户进行登录");
        }

        model.addAttribute("userName", map.get("userName"));
        model.addAttribute("userId", map.get("userId"));
        model.addAttribute("userEmail", map.get("userEmail"));
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
            HttpServletRequest request,
            @PathVariable(value = "modelId") Integer modelId,
            @PathVariable(value = "modelName") String modelName){
        Map<String, Object> map = GetUserVo.getUserInfo(request);
        if(map.get("Biotoken").equals("") || map.get("userName") == null || map.get("userName").equals("")){
            System.out.println("提醒用户进行登录");
        }
        model.addAttribute("userName", map.get("userName"));
        model.addAttribute("userId", map.get("userId"));
        model.addAttribute("userEmail", map.get("userEmail"));
        model.addAttribute("modelId", modelId);
        model.addAttribute("modelName", modelName);

        return "selectEncodeAndInput";
    }

}
