package com.online.college.portal.business;

import com.online.college.portal.vo.CourseSectionVO;

import java.util.List;

/**
 * Created by RookieWangZhiWei on 2018/5/5.
 */
public interface ICourseBusiness {
    List<CourseSectionVO> queryCourseSection(Long courseId);
}
