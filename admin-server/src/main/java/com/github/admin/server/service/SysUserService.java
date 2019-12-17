package com.github.admin.server.service;

import com.github.admin.server.dao.SysUserMapper;
import com.github.admin.server.model.SysUser;
import com.github.foundation.service.BaseService;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:24
 */
public interface SysUserService extends BaseService<SysUser, SysUserMapper> {

    /**
     * 根据用户名获取用户
     * @param userName 用户名
     * @return 用户信息
     */
    SysUser getByName(String userName);

    /**
     * 注册用户
     * @param sysUser 用户信息
     * @return 用户id
     */
    Long register(SysUser sysUser);
}
