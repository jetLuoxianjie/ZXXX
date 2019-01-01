package com.online.college.portal.business.impl;

import com.online.college.core.consts.CourseEnum;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;
import com.online.college.portal.business.ICourseBusiness;
import com.online.college.portal.vo.CourseSectionVO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *
 * @author RookieWangZhiWei
 * @date 2018/5/24
 */
@Service
public class CourseBusinessImpl  implements ICourseBusiness {

    @Autowired
    private ICourseSectionService courseSectionService;


    @Override
    public List<CourseSectionVO> queryCourseSection(Long courseId) {
        List<CourseSectionVO> resultList = new ArrayList<CourseSectionVO>();

        CourseSection queryEntity = new CourseSection();

        queryEntity.setCourseId(courseId);
        queryEntity.setOnsale(CourseEnum.ONSALE.value());

        Map<Long,CourseSectionVO> tmpMap = new LinkedHashMap<Long,CourseSectionVO>();

        Iterator<CourseSection> it = courseSectionService.queryAll(queryEntity).iterator();

        while(it.hasNext()){
                CourseSection item = it.next();
                if (Long.valueOf(0).equals(item.getParentId())){
                    CourseSectionVO vo = new CourseSectionVO();
                    BeanUtils.copyProperties(item, vo);
                    tmpMap.put(vo.getId(),vo);
                }else{
                    tmpMap.get(item.getParentId()).getSections().add(item);
                }
        }

        for (CourseSectionVO vo :
                tmpMap.values()) {
            resultList.add(vo);
        }

        return resultList;

    }
}
