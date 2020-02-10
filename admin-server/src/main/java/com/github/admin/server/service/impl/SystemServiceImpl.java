package com.github.admin.server.service.impl;

import com.github.admin.server.dao.SystemMapper;
import com.github.admin.server.model.System;
import com.github.admin.server.service.MenuService;
import com.github.admin.server.service.SystemService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/7 14:48
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<System, SystemMapper> implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void delete(Long id) {
        System system = systemMapper.selectByPrimaryKey(id);
        if (CollectionUtils.isNotEmpty(menuService.getBySystemCode(system.getCode()))) {
            throw new BusinessException("该系统已被使用，不能删除");
        }
        systemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void create(System system) {
        Example example = new Example(System.class);
        example.createCriteria().andEqualTo("code", system.getCode());
        if (systemMapper.selectOneByExample(example) != null) {
            throw new BusinessException("系统编码不能重复");
        }
        system.setCreateTime(new Date());
        system.setCreator(authenticationManager.getUserName());
        system.setModifyTime(new Date());
        system.setModifier(authenticationManager.getUserName());
        systemMapper.insertUseGeneratedKeys(system);
    }

    @Override
    public void update(System system) {
        Example example = new Example(System.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", system.getCode());
        criteria.andNotEqualTo("id", system.getId());
        if (systemMapper.selectOneByExample(example) != null) {
            throw new BusinessException("系统编码不能重复");
        }
        system.setModifier(authenticationManager.getUserName());
        system.setModifyTime(new Date());
        systemMapper.updateByPrimaryKeySelective(system);
    }
}
