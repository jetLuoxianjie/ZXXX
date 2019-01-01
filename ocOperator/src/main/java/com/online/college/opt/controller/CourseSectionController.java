package com.online.college.opt.controller;

import com.online.college.common.web.JsonView;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;
import com.online.college.opt.business.ICourseSectionBusiness;
import com.online.college.opt.vo.CourseSectionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Controller
@RequestMapping(value = "/courseSection")
public class CourseSectionController {

    @Autowired
    private ICourseSectionService courseSectionService;

    @Autowired
    private ICourseSectionBusiness courseSectionBusiness;

    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id) {
        return JsonView.render(courseSectionService.getById(id));
    }

    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(CourseSection entity) {
        courseSectionService.updateSelectivity(entity);
        return new JsonView().toString();
    }


    @RequestMapping(value = "/sortSection")
    @ResponseBody
    public String sortSection(CourseSection entity, Integer sortType) {
        CourseSection curCourseSection = courseSectionService.getById(entity.getId());
        if (null != curCourseSection) {
            CourseSection tmpCourseSection = null;
            if (Integer.valueOf(1).equals(sortType)) {
                tmpCourseSection = courseSectionService.getSortSectionMax(curCourseSection);
            } else {
                tmpCourseSection = courseSectionService.getSortSectionMin(curCourseSection);
            }
            if (null != tmpCourseSection) {
                Integer tmpSort = curCourseSection.getSort();
                curCourseSection.setSort(tmpCourseSection.getSort());
                courseSectionService.updateSelectivity(curCourseSection);

                tmpCourseSection.setSort(tmpSort);
                courseSectionService.updateSelectivity(tmpCourseSection);

            }
        }
        return new JsonView().toString();
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public String delete(CourseSection entity) {
        courseSectionService.delete(entity);
        return new JsonView().toString();
    }


    @RequestMapping(value = "/deleteLogic")
    @ResponseBody
    public String deleteLogic(CourseSection entity) {
        courseSectionService.deleteLogic(entity);
        return new JsonView().toString();
    }


    @RequestMapping(value = "/batchAdd")
    @ResponseBody
    public String batchAdd(@RequestBody List<CourseSectionVO> batchSections) {
        courseSectionBusiness.batchAdd(batchSections);
        return new JsonView().toString();
    }


    @RequestMapping(value = "/doImport")
    @ResponseBody
    public String doImport(Long courseId, @RequestParam(value = "courseSectionExcel", required = true) MultipartFile excelFile) {
        try {
            if (null != excelFile && excelFile.getBytes().length > 0) {
                InputStream is = excelFile.getInputStream();
                courseSectionBusiness.batchImport(courseId, is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JsonView().toString();
    }


}
