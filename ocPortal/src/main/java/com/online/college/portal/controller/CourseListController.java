package com.online.college.portal.controller;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalBusiness;
import com.online.college.portal.vo.ConstsClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/course")
public class CourseListController {

    @Autowired
    private IConstsClassifyService constsClassifyService;

    @Autowired
    private IPortalBusiness portalBusiness;

    @Autowired
    private ICourseService courseService;


    @RequestMapping(value = "/list")
    public ModelAndView list(String c, String sort, TailPage<Course> page){
        ModelAndView mv = new ModelAndView("list");
        String curCode = "-1";
        String curSubCode = "-2";


        Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();

        List<ConstsClassifyVO> classifysList = new ArrayList<>();

        for (ConstsClassifyVO vo:
             classifyMap.values()) {
            classifysList.add(vo);
        }
        mv.addObject("classifys",classifysList);

        ConstsClassify curClassify =   constsClassifyService.getByCode(c);

        if(null == curClassify){
            List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
            for(ConstsClassifyVO vo : classifyMap.values()){
                subClassifys.addAll(vo.getSubClassifyList());
            }
            mv.addObject("subClassifys", subClassifys);
        }else{
            if(!"0".endsWith(curClassify.getParentCode())){
                curSubCode = curClassify.getCode();
                curCode = curClassify.getParentCode();
                mv.addObject("subClassifys", classifyMap.get(curClassify.getParentCode()).getSubClassifyList());
            }else{
                curCode = curClassify.getCode();
                mv.addObject("subClassifys", classifyMap.get(curClassify.getCode()).getSubClassifyList());
            }
        }

        mv.addObject("curCode",curCode);
        mv.addObject("curSubCode",curSubCode);


        Course queryEntity = new Course();
        if (!"-1".equals(curCode)){
            queryEntity.setClassify(curCode);
        }
        if (!"-2".equals(curSubCode)){
            queryEntity.setSubClassify(curSubCode);
        }



        if ("pop".equals(sort)){
            page.descSortField("studyCount");
        }else{
            sort = "last";
            page.descSortField("id");
        }

        mv.addObject("sort",sort) ;


        queryEntity.setOnsale(CourseEnum.ONSALE.value());
        page = this.courseService.queryPage(queryEntity,page);
        mv.addObject("page",page);

        return mv;

    }

}
