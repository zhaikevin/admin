package com.github.admin.server.service.impl;

import com.github.admin.server.dao.SystemMapper;
import com.github.admin.server.model.System;
import com.github.admin.server.service.MenuService;
import com.github.admin.server.service.SystemService;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void delete(Long id) {
        if (CollectionUtils.isNotEmpty(menuService.getBySystemId(id))) {
            throw new BusinessException("该系统已被使用，不能删除");
        }
        systemMapper.deleteByPrimaryKey(id);
    }
}
