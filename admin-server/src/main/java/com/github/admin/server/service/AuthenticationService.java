package com.github.admin.server.service;

import com.github.admin.server.dao.AuthenticationMapper;
import com.github.admin.server.model.Authentication;
import com.github.foundation.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface AuthenticationService extends BaseService<Authentication, AuthenticationMapper> {

    /**
     * 根据角色删除权限
     * @param roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据菜单批量删除
     * @param menuIdList
     */
    void batchDeleteByMenuId(List<Long> menuIdList);

    /**
     * 保存权限
     * @param list
     */
    void save(List<Authentication> list, Long roleId);

    /**
     * 根据角色id得到所有的菜单id
     * @param roleId
     * @return
     */
    List<Long> getMenuIdByRoleId(Long roleId);

}
