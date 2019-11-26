package com.github.admin.service;

import com.github.admin.model.SysUser;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:24
 */
public interface SysUserService {

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
