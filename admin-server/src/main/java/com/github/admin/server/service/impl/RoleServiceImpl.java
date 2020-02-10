package com.github.admin.server.service.impl;

import com.github.admin.server.dao.RoleMapper;
import com.github.admin.server.model.Role;
import com.github.admin.server.model.System;
import com.github.admin.server.service.AuthenticationService;
import com.github.admin.server.service.RoleService;
import com.github.admin.server.service.UserRoleService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

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

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        userRoleService.delete(null, id);
        authenticationService.deleteByRoleId(id);
    }

    @Override
    public void create(Role role) {
        Example example = new Example(System.class);
        example.createCriteria().andEqualTo("code", role.getCode());
        if (roleMapper.selectOneByExample(example) != null) {
            throw new BusinessException("角色编码不能重复");
        }
        role.setCreateTime(new Date());
        role.setCreator(authenticationManager.getUserName());
        role.setModifyTime(new Date());
        role.setModifier(authenticationManager.getUserName());
        roleMapper.insertUseGeneratedKeys(role);
    }

    @Override
    public void modify(Role role) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", role.getCode());
        criteria.andNotEqualTo("id", role.getId());
        if (roleMapper.selectOneByExample(example) != null) {
            throw new BusinessException("角色编码不能重复");
        }
        role.setModifier(authenticationManager.getUserName());
        role.setModifyTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role getByCode(String code) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        return roleMapper.selectOneByExample(example);
    }
}
