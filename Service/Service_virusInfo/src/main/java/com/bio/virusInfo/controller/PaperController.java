package com.bio.virusInfo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bio.entityModel.model.virusPaper.Paper;
import com.bio.virusInfo.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class PaperController {
    @Autowired
    private PaperService paperService;
//    跳转到paper界面并分页显示
    /**
     * time 是时间条件
     * order 是按照引用书继续进行拍寻
     * 这两个可以组合
     * **/
    @RequestMapping("/allPaperPage")
    public String allPaperHtml(Model model,
                               @RequestParam(value = "pn", defaultValue = "1")Integer pn,
                               @RequestParam(value = "time",required = false)String time,
                               @RequestParam(value = "order",required = false)String order){
//        ?xx=yy&zz=ww 要用@RequestParam
//        @RequestMapping(value = "/user/{username}/blog/{blogId}") 要用 @PathVariable
        int pageSize = 10;
        Page<Paper> paperPage = new Page<>(pn, pageSize,true);

        log.info("time:"+ time);
        log.info("order:"+ order);
//       传到这里的情况中，
//       有可能time为空 order 不空，
//       也有可能order为空 time不空，
//       也有可能都为空，
//       也有可能全都存在，
//       所以一共是四种情况
//        特别注意一点，为空有两种情况，首先是equals("")，其次是为null

//        1 全为空的情况，就是默认paper表的全部内容
        if((time == null || time.isEmpty()) && (order == null || order.isEmpty())){
            log.info("情况1");
            Page<Paper> page = paperService.selectPaperByPage(paperPage);//先无条件显示全部数据分页
            System.out.println("total: " + page.getTotal());
            model.addAttribute("page", page);
        }
//        2 order不为空 time不空,那就是按照年份条件得到结果，并order by引用数，判断其order内容选择正反序列 positive->ASC递增 reverse->desc递减
        else if((time != null && !time.isEmpty()) && (order != null && !order.isEmpty())){
            log.info("情况2");
            Page<Paper> page = paperService.selectPaperByTimeCitationsPage(paperPage, time, order);//
            System.out.println("total: " + page.getTotal());
            model.addAttribute("page", page);
        }
//        3 也有order为空 time不空，那就是按照年份条件得到结果
        else if (time != null && !time.isEmpty()){
            log.info("情况3");
            Page<Paper> page = paperService.selectPaperByTimePage(paperPage, time);//
            System.out.println("total: " + page.getTotal());
            model.addAttribute("page", page);
        }
//        4 也有order不为空 time空，那就是默认排序下，order by引用数，判断其order内容选择正反序列 positive->ASC递增 reverse->desc递减
        else if (order != null && !order.isEmpty()){
            log.info("情况4");
            Page<Paper> page = paperService.selectPaperByCitationsPage(paperPage, order);
            System.out.println("total: " + page.getTotal());
            model.addAttribute("page", page);
        }
        //传入时间条件的前缀
        model.addAttribute("preflex","http://localhost:8151/allPaperPage?pn=1&time=");
        model.addAttribute("time",time);
        model.addAttribute("order",order);
        return "allPaperPage";
    }
}
