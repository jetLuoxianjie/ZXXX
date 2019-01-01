package com.online.college.core.auth.dao;

import com.online.college.common.page.TailPage;
import com.online.college.core.auth.domain.AuthUser;
import com.qiniu.util.Auth;

import java.util.List;

/**
 * Created by RookieWangZhiWei on 2018/5/4.
 */
public interface AuthUserDao {

    public  AuthUser getById(Long id);

    public  AuthUser getByUsername(String username);

    public AuthUser getByUsernameAndPassword(AuthUser authUser);


    public List<AuthUser> queryRecomd();

    public Integer getTotalItemsCount(AuthUser queryEntity);

    public List<AuthUser> queryPage(AuthUser queryEntity, TailPage<AuthUser> page);

    public void createSelectivity(AuthUser entity);

    public void update(AuthUser entity);

    public void updateSelectivity(AuthUser entity);

    public void delete(AuthUser entity);

    public void deleteLogic(AuthUser entity);


}
