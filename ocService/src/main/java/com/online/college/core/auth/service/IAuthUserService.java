package com.online.college.core.auth.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.auth.domain.AuthUser;

import java.util.List;

/**
 * Created by RookieWangZhiWei on 2018/5/4.
 */
public interface IAuthUserService {

    public AuthUser getById(Long id);

    public AuthUser getByUsername(String username);

    public AuthUser getByUsernameAndPassword(AuthUser authUser);


    public List<AuthUser> queryRecomd();

    public void createSelectivity(AuthUser entity);

    public TailPage<AuthUser> queryPage(AuthUser queryEntity, TailPage<AuthUser> page);


    public void update(AuthUser entity);

    public void updateSelectivity(AuthUser entity);

    public void delete(AuthUser entity);

    public void deleteLogin(AuthUser entity);

}
