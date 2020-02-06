package com.github.admin.server.service.impl;

import com.github.admin.server.dao.AuthenticationMapper;
import com.github.admin.server.model.Authentication;
import com.github.admin.server.service.AuthenticationService;
import com.github.foundation.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:15
 */
@Service
public class AuthenticationServiceImpl extends BaseServiceImpl<Authentication, AuthenticationMapper> implements AuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Override
    public void deleteByRoleId(Long roleId) {
        Example example = new Example(Authentication.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        authenticationMapper.deleteByExample(example);
    }

    @Override
    public void batchDeleteByMenuId(List<Long> menuIdList) {
        Example example = new Example(Authentication.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("menuId", menuIdList);
        authenticationMapper.deleteByExample(example);
    }

    @Override
    public void save(List<Authentication> list) {

    }

    @Override
    public List<String> getMenuCodeByRoleId(Long roleId) {
        Example example = new Example(Authentication.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<Authentication> list = authenticationMapper.selectByExample(example);
        List<String> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(item -> result.add(item.getMenuCode()));
        }
        return result;
    }
}
