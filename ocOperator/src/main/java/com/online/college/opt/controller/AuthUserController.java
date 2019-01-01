package com.online.college.opt.controller;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class AuthUserController {


    @Autowired
    private IAuthUserService authUserService;

    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id){
        AuthUser user = authUserService.getById(id);
        return JsonView.render(user);
    }



    @RequestMapping(value = "/userPageList")
    public ModelAndView queryPage(AuthUser queryEntity, TailPage<AuthUser> page){
        ModelAndView mv = new ModelAndView("cms/user/userPageList");
        mv.addObject("curNav","user");


        if (StringUtils.isNotEmpty(queryEntity.getUsername())){
            queryEntity.setUsername(queryEntity.getUsername().trim());
        }else{
            queryEntity.setUsername(null);
        }

        page = authUserService.queryPage(queryEntity, page);
        mv.addObject("page",page);
        mv.addObject("queryEntity",queryEntity);

        return mv;

    }


    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(AuthUser entity){
        entity.setUsername(null);
        entity.setPassword(null);
        authUserService.updateSelectivity(entity);
        return new JsonView(0).toString();
    }
}
