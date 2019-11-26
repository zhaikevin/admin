package com.github.admin.service.impl;

import com.github.admin.constant.SysUserState;
import com.github.admin.model.SysUser;
import com.github.admin.service.SysUserService;
import com.github.foundation.authentication.FoundationUserService;
import com.github.foundation.authentication.model.FoundationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/18 17:11
 */
@Service
public class FoundationUserServiceImpl implements FoundationUserService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public FoundationUser getByName(String userName) {
        FoundationUser foundationUser = new FoundationUser();
        SysUser user = sysUserService.getByName(userName);
        if (user == null) {
            return null;
        }
        foundationUser.setUserName(user.getUsername());
        foundationUser.setUserId(user.getId());
        foundationUser.setPassword(user.getPassword());
        foundationUser.setUserSalt(user.getSalt());
        foundationUser.setIsValid(SysUserState.VALID.getVal().equals(user.getStatus()));
        return foundationUser;
    }

    @Override
    public Set<String> getPermissions(Long userId) {
        return null;
    }
}
