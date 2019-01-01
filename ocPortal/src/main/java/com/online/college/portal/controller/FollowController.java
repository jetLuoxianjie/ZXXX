package com.online.college.portal.controller;

import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.user.domain.UserFollows;
import com.online.college.core.user.service.IUserFollowsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private IUserFollowsService userFollowsService;

    @RequestMapping(value = "/doFollow")
    @ResponseBody
    public String doFollow(Long followId){
        Long curUserId = SessionContext.getUserId();
        UserFollows userFollows = new UserFollows();

        userFollows.setUserId(curUserId);
        userFollows.setFollowId(followId);
        List<UserFollows> list = userFollowsService.queryAll(userFollows);

        if (CollectionUtils.isNotEmpty(list)){
            userFollowsService.delete(list.get(0));
            return new JsonView(0).toString();
        }else{
            userFollows.setCreateTime(new Date());
            userFollowsService.createSelectivity(userFollows);
            return new JsonView(1).toString();
        }
    }


    @RequestMapping(value = "/isFollow")
    @ResponseBody
    public String isFollow(Long followId){
        Long curUserId = SessionContext.getUserId();
        UserFollows userFollows = new UserFollows();

        userFollows.setUserId(curUserId);
        userFollows.setFollowId(followId);

        List<UserFollows> list = userFollowsService.queryAll(userFollows);

        if (CollectionUtils.isNotEmpty(list)){
            return new JsonView(1).toString();
        }else {
            return new JsonView(0).toString();
        }

    }

}
