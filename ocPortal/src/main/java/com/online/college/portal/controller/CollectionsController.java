package com.online.college.portal.controller;

import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.user.domain.UserCollections;
import com.online.college.core.user.service.IUserCollectionsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/collections")
public class CollectionsController {

    @Autowired
    private IUserCollectionsService userCollectionsService;


    @RequestMapping(value = "/doCollection")
    @ResponseBody
    public String doCollection(Long courseId){
        Long curUserId = SessionContext.getUserId();
        UserCollections userCollections = new UserCollections();


        userCollections.setUserId(curUserId);
        userCollections.setClassify(CourseEnum.COLLECTION_CLASSIFY_COURSE.value());
        userCollections.setObjectId(courseId);

        List<UserCollections> list = userCollectionsService.queryAll(userCollections);

        if (CollectionUtils.isNotEmpty(list)){
            userCollectionsService.delete(list.get(0));
            return new JsonView(0).toString();
        }else{
            userCollections.setCreateTime(new Date());
            userCollectionsService.createSelectivity(userCollections);
            return new JsonView(1).toString();
        }
    }


    @RequestMapping(value = "/isCollection")
    @ResponseBody
    public String isCollection(Long courseId){
        Long curUserId =SessionContext.getUserId();
        UserCollections userCollections = new UserCollections();

        userCollections.setUserId(curUserId);
        userCollections.setClassify(CourseEnum.COLLECTION_CLASSIFY_COURSE.value());
        userCollections.setObjectId(courseId);

        List<UserCollections> list = userCollectionsService.queryAll(userCollections);

        if (CollectionUtils.isNotEmpty(list)){
            return new JsonView(1).toString();

        }else{

            return new JsonView(0).toString();
        }
    }

}
