package com.github.admin.server.service.impl;

import com.github.admin.server.dao.UserGroupMapper;
import com.github.admin.server.model.UserGroup;
import com.github.admin.server.service.UserGroupRelService;
import com.github.admin.server.service.UserGroupService;
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
public class UserGroupServiceImpl extends BaseServiceImpl<UserGroup, UserGroupMapper> implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserGroupRelService userGroupRelService;

    @Override
    @Transactional
    public void delete(Long id) {
        userGroupMapper.deleteByPrimaryKey(id);
        userGroupRelService.delete(null,id);
        //TODO
    }

    @Override
    public void create(UserGroup userGroup) {
        Example example = new Example(UserGroup.class);
        example.createCriteria().andEqualTo("code", userGroup.getCode());
        if (userGroupMapper.selectOneByExample(example) != null) {
            throw new BusinessException("用户组编码不能重复");
        }
        userGroup.setCreateTime(new Date());
        userGroup.setCreator(authenticationManager.getUserName());
        userGroup.setModifyTime(new Date());
        userGroup.setModifier(authenticationManager.getUserName());
        userGroupMapper.insertUseGeneratedKeys(userGroup);
    }

    @Override
    public void modify(UserGroup userGroup) {
        Example example = new Example(UserGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", userGroup.getCode());
        criteria.andNotEqualTo("id", userGroup.getId());
        if (userGroupMapper.selectOneByExample(example) != null) {
            throw new BusinessException("角色编码不能重复");
        }
        userGroup.setModifier(authenticationManager.getUserName());
        userGroup.setModifyTime(new Date());
        userGroupMapper.updateByPrimaryKeySelective(userGroup);
    }

    @Override
    public UserGroup getByCode(String code) {
        Example example = new Example(UserGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        return userGroupMapper.selectOneByExample(example);
    }
}
