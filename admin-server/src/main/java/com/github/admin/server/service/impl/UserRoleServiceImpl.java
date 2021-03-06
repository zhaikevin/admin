package com.github.admin.server.service.impl;

import com.github.admin.server.dao.UserRoleMapper;
import com.github.admin.server.model.UserRole;
import com.github.admin.server.service.UserRoleService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.common.utils.ValidateUtils;
import com.github.foundation.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:15
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, UserRoleMapper> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void save(Long userId, Long roleId, String userName) {
        save(userId, roleId, userName, authenticationManager.getUserName());
    }

    @Override
    public void save(Long userId, Long roleId, String userName, String operator) {
        ValidateUtils.notEmptyString(userName, "用户名不能为空");
        ValidateUtils.notNull(userId, "用户id不能为空");
        ValidateUtils.notNull(roleId, "角色id不能为空");
        ValidateUtils.notEmptyString(operator, "操作人不能为空");
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("roleId", roleId);
        UserRole userRole = userRoleMapper.selectOneByExample(example);
        if (userRole != null) {
            userRole.setModifyTime(new Date());
            userRole.setModifier(operator);
            userRoleMapper.updateByPrimaryKeySelective(userRole);
        } else {
            userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setUserName(userName);
            userRole.setCreateTime(new Date());
            userRole.setCreator(operator);
            userRole.setModifyTime(new Date());
            userRole.setModifier(operator);
            userRoleMapper.insertUseGeneratedKeys(userRole);
        }
    }

    @Override
    public void delete(Long userId, Long roleId) {
        if (userId == null && roleId == null) {
            throw new BusinessException("用户id和角色id不能同时为空");
        }
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andEqualTo("userId", userId);
        }
        if (roleId != null) {
            criteria.andEqualTo("roleId", roleId);
        }
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public List<UserRole> getByUserId(Long userId) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return userRoleMapper.selectByExample(example);
    }
}
