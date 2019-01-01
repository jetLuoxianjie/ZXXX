package com.online.college.portal.controller;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.course.domain.CourseComment;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseCommentService;
import com.online.college.core.course.service.ICourseSectionService;
import com.qiniu.util.Json;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
@RequestMapping("/courseComment")
public class CourseCommentController {

    @Autowired
    private ICourseCommentService courseCommentService;

    @Autowired
    private ICourseSectionService courseSectionService;



    @RequestMapping(value = "/segment")
    public ModelAndView segment(CourseComment queryEntity,TailPage<CourseComment> page) {

        if (null == queryEntity.getCourseId() || queryEntity.getType() == null) {
            return new ModelAndView("error/404");
        }

        ModelAndView mv = new ModelAndView("commentSegment");
        TailPage<CourseComment> commentPage = this.courseCommentService.queryPage(queryEntity, page);

        for (CourseComment item :
                commentPage.getItems()) {
            if (StringUtils.isNotEmpty(item.getHeader())) {
                item.setHeader(QiniuStorage.getUrl(item.getHeader()));
            }
        }

        mv.addObject("page",commentPage);

        return mv;
    }





    @RequestMapping(value = "/doComment")
    @ResponseBody
    public String doComment(HttpServletRequest request, CourseComment entity, String indeityCode){
        if (null == indeityCode || (indeityCode != null && indeityCode.equalsIgnoreCase(SessionContext.getIdentifyCode(request)))){
            return new JsonView(2).toString();
        }
        if (entity.getContent().trim().length() > 200 ||entity.getContent().trim().length()==0){
            return new JsonView(3).toString();
        }
        if (null != entity.getRefId()){
            CourseComment refComment = this.courseCommentService.getById(entity.getRefId());
            if (null != refComment){
                CourseSection courseSection = courseSectionService.getById(refComment.getSectionId());
                if(null != courseSection){
                    entity.setRefContent(refComment.getContent());
                    entity.setRefId(entity.getRefId());
                    entity.setCourseId(refComment.getCourseId());
                    entity.setSectionId(refComment.getSectionId());
                    entity.setSectionTitle(courseSection.getName());

                    entity.setToUsername(refComment.getUsername());//引用的评论的username
                    entity.setUsername(SessionContext.getUsername());
                    entity.setCreateTime(new Date());
                    entity.setCreateUser(SessionContext.getUsername());
                    entity.setUpdateTime(new Date());
                    entity.setUpdateUser(SessionContext.getUsername());

                    this.courseCommentService.createSelectivity(entity);
                    return new JsonView(0).toString();
                }
            }
        }else{
            CourseSection courseSection = courseSectionService.getById(entity.getSectionId());
            if (null != courseSection){
                entity.setSectionTitle(courseSection.getName());
                entity.setToUsername(entity.getCreateUser());//toUsername可以作为页面入参
                entity.setUsername(SessionContext.getUsername());
                entity.setCreateTime(new Date());
                entity.setCreateUser(SessionContext.getUsername());
                entity.setUpdateTime(new Date());
                entity.setUpdateUser(SessionContext.getUsername());


                this.courseCommentService.createSelectivity(entity);
                return new JsonView(0).toString();
            }
        }

        return new JsonView(1).toString();
    }

}
