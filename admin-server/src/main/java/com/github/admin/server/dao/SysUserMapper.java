package com.github.admin.server.dao;

import com.github.admin.server.model.SysUser;
import com.github.foundation.datasource.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:23
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询，去掉跟角色有对应关系的用户
     * @param roleId
     * @return
     */
    List<SysUser> listByRole(Long roleId, String username);

    /**
     * 分页查询，去掉跟用户组有对应关系的用户
     * @param groupId
     * @return
     */
    List<SysUser> listByGroup(Long groupId, String username);
}
