package com.online.college.portal.vo;

import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.course.domain.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RookieWangZhiWei on 2018/5/4.
 */
public class ConstsClassifyVO  extends ConstsClassify{

    private static final long serialVersionUID = -6898939223836635781L;


    private List<ConstsClassify> subClassifyList = new ArrayList<ConstsClassify>();

    private List<Course> recomdeCourseList;

    public List<ConstsClassify> getSubClassifyList() {
        return subClassifyList;
    }

    public void setSubClassifyList(List<ConstsClassify> subClassifyList) {
        this.subClassifyList = subClassifyList;
    }

    public List<Course> getRecomdCourseList() {
        return recomdeCourseList;
    }

    public void setRecomdeCourseList(List<Course> recomdeCourseList) {
        this.recomdeCourseList = recomdeCourseList;
    }
}
