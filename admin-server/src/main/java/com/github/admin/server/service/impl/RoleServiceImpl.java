package com.github.admin.server.service.impl;

import com.github.admin.server.dao.RoleMapper;
import com.github.admin.server.model.Role;
import com.github.admin.server.service.RoleService;
import com.github.admin.server.service.UserRoleService;
import com.github.foundation.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:15
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleMapper> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    @Transactional
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        userRoleService.delete(null, id);
    }
}
