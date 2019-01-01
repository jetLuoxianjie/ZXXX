package com.online.college.core.auth.service.impl;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.core.auth.dao.AuthUserDao;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by RookieWangZhiWei on 2018/5/4.
 */
@Service
public class AuthUserServiceImpl implements IAuthUserService {

    @Autowired
    private AuthUserDao authUserDao;

    @Override
    public AuthUser getById(Long id) {
        return authUserDao.getById(id);
    }

    @Override
    public AuthUser getByUsername(String username) {
        return authUserDao.getByUsername(username);
    }

    @Override
    public AuthUser getByUsernameAndPassword(AuthUser authUser) {
        return authUserDao.getByUsernameAndPassword(authUser);
    }

    @Override
    public List<AuthUser> queryRecomd() {
        List<AuthUser> recomdList = authUserDao.queryRecomd();
        if (CollectionUtils.isNotEmpty(recomdList)) {
            for (AuthUser user :
                    recomdList) {
                if (StringUtils.isNotEmpty(user.getHeader())) {
                    user.setHeader(QiniuStorage.getUrl(user.getHeader()));
                }
            }
        }
        return recomdList;
    }

    @Override
    public void createSelectivity(AuthUser entity) {
        authUserDao.createSelectivity(entity);
    }


    @Override
    public TailPage<AuthUser> queryPage(AuthUser queryEntity, TailPage<AuthUser> page) {
        Integer itemsTotalCount = authUserDao.getTotalItemsCount(queryEntity);

        List<AuthUser> items = authUserDao.queryPage(queryEntity, page);

        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);

        return page;
    }


    @Override
    public void update(AuthUser entity) {
        authUserDao.update(entity);
    }

    @Override
    public void updateSelectivity(AuthUser entity) {
        authUserDao.updateSelectivity(entity);
    }

    @Override
    public void delete(AuthUser entity) {
        authUserDao.delete(entity);
    }

    @Override
    public void deleteLogin(AuthUser entity) {
        authUserDao.deleteLogic(entity);
    }
}
