package com.github.admin.server.service;

import com.github.admin.server.dao.RoleMapper;
import com.github.admin.server.model.Role;
import com.github.foundation.service.BaseService;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface RoleService extends BaseService<Role, RoleMapper> {

    /**
     * 删除
     * @param id
     */
    void delete(Long id);
}
