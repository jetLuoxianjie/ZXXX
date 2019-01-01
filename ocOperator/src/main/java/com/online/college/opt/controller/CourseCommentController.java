package com.online.college.opt.controller;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.web.JsonView;
import com.online.college.core.course.domain.CourseComment;
import com.online.college.core.course.service.ICourseCommentService;
import com.qiniu.util.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/courseComment")
public class CourseCommentController {

    @Autowired
    private ICourseCommentService courseCommentService;


    @RequestMapping(value = "/pagelist")
    public ModelAndView commentSegment(CourseComment queryEntity, TailPage<CourseComment> page) {
        ModelAndView mv = new ModelAndView("cms/course/readComment");
        queryEntity.setCourseId(1L);
        TailPage<CourseComment> commentPage = courseCommentService.queryPage(queryEntity, page);

        for (CourseComment item :
                commentPage.getItems()) {
            if (StringUtils.isNotEmpty(item.getHeader())) {
                item.setHeader(QiniuStorage.getUrl(item.getHeader()));
            }
        }

        mv.addObject("page", commentPage);
        mv.addObject("queryEntity", queryEntity);

        return mv;
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(CourseComment entity) {
        courseCommentService.delete(entity);

        return new JsonView().toString();
    }
}
