package com.online.college.portal.business;

import com.online.college.portal.vo.ConstsClassifyVO;

import java.util.List;
import java.util.Map;

/**
 * Created by RookieWangZhiWei on 2018/5/4.
 */
public interface IPortalBusiness {

    /**
     * 获取所有，包括一级分类&二级分类
     */
    List<ConstsClassifyVO> queryAllClassify();

    /**
     * 获取所有分类
     */
    Map<String,ConstsClassifyVO> queryAllClassifyMap();

    /**
     * 为分类设置课程推荐
     */
    void prepareRecomdCourses(List<ConstsClassifyVO> classifyVoList);
}
