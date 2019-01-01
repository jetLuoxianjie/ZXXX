package com.online.college.opt.controller;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.opt.business.IPortalBusiness;
import com.online.college.opt.vo.ConstsClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/classify")
public class ClassifyController {


    @Autowired
    private IConstsClassifyService ConstsClassifyService;

    @Autowired
    private IPortalBusiness portalBusiness;


    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id){
        return JsonView.render(ConstsClassifyService.getById(id));
    }


    @RequestMapping("/index")
    public ModelAndView classifyIndex(ConstsClassify queryEntity, TailPage<ConstsClassify> page){
        ModelAndView mv = new ModelAndView("cms/classify/classifyIndex");
        mv.addObject("curNav","classify");

        Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();

        List<ConstsClassifyVO> classifyList = new ArrayList<>();
        for (ConstsClassifyVO vo :
                classifyMap.values()) {
            classifyList.add(vo);
        }
        mv.addObject("classifys",classifyList);

        List<ConstsClassify> subClassifys = new ArrayList<>();

        for (ConstsClassifyVO vo :
                classifyMap.values()) {
            subClassifys.addAll(vo.getSubClassifyList());
        }
        mv.addObject("subClassifys",subClassifys);

        return mv;

    }

    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(ConstsClassify entity){
        if (entity.getId() == null){
            ConstsClassify tmpEntity = ConstsClassifyService.getByCode(entity.getCode());
            if (tmpEntity != null){
                return JsonView.render(1,"此编码已存在");
            }
            ConstsClassifyService.createSelectivity(entity);
        }else{
            ConstsClassifyService.updateSelectivity(entity);
        }

        return new JsonView(0).toString();
    }


    @RequestMapping(value = "/deleteLogic")
    @ResponseBody
    public String deleteLogic(ConstsClassify entity){
        ConstsClassifyService.deleteLogic(entity);
        return new JsonView().toString();
    }
}
