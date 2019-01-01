package com.online.college.portal.business.impl;

import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalBusiness;
import com.online.college.portal.vo.ConstsClassifyVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by RookieWangZhiWei on 2018/5/4.
 */
@Service
public class PortalBusinessImpl implements IPortalBusiness {


    @Autowired
    private IConstsClassifyService constsClassifyService;

    @Autowired
    private ICourseService courseService;

    /**
     * 获取所有，包括一级分类&二级分类
     */
    @Override
    public List<ConstsClassifyVO> queryAllClassify() {
        List<ConstsClassifyVO> resultList = new ArrayList<ConstsClassifyVO>();
        for (ConstsClassifyVO vo :
                this.queryAllClassifyMap().values()) {
            resultList.add(vo);
        }
        return resultList;
    }
    /*
     获取所有分类
     */
    @Override
    public Map<String, ConstsClassifyVO> queryAllClassifyMap() {
        Map<String, ConstsClassifyVO> resultMap = new LinkedHashMap<String, ConstsClassifyVO>();

        Iterator<ConstsClassify> it = constsClassifyService.queryAll().iterator();
        while (it.hasNext()) {
            ConstsClassify c = it.next();
            if ("0".equals(c.getParentCode())) {
                ConstsClassifyVO vo = new ConstsClassifyVO();
                BeanUtils.copyProperties(c, vo);
                resultMap.put(vo.getCode(), vo);
            } else {
                if (null != resultMap.get(c.getParentCode())) {
                    resultMap.get(c.getParentCode()).getSubClassifyList().add(c);
                }
            }
        }
        return resultMap;
    }

    @Override
    public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVoList) {
        if (CollectionUtils.isNotEmpty(classifyVoList)) {
            for (ConstsClassifyVO item :
                    classifyVoList) {
                CourseQueryDto queryEntity = new CourseQueryDto();
                queryEntity.setCount(5);
                queryEntity.descSortField("weight");
                queryEntity.setClassify(item.getCode());

                List<Course> tmpList = this.courseService.queryList(queryEntity);
                if (CollectionUtils.isNotEmpty(tmpList)) {
                    item.setRecomdeCourseList(tmpList);
                }
            }
        }
    }
}
