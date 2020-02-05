package com.github.admin.server.service;

import com.github.admin.server.dao.SysUserMapper;
import com.github.admin.server.model.SysUser;
import com.github.foundation.pagination.model.Pagination;
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
     * 创建用户
     * @param sysUser
     * @return
     */
    Long create(SysUser sysUser, String operation);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 重置密码
     * @param sysUser
     */
    void resetPassword(SysUser sysUser);

    /**
     * 分页查询，去掉跟角色有对应关系的用户
     * @param pagination
     * @param roleId
     */
    void listByRole(Long roleId, String username, Pagination<SysUser> pagination);
}
