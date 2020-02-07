package com.github.admin.server.service.impl;

import com.github.admin.server.dao.AuthenticationMapper;
import com.github.admin.server.model.Authentication;
import com.github.admin.server.service.AuthenticationService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:15
 */
@Service
public class AuthenticationServiceImpl extends BaseServiceImpl<Authentication, AuthenticationMapper> implements AuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    @Transactional
    public void save(List<Authentication> list, Long roleId) {
        List<Authentication> oldAuthentications = getByRoleId(roleId);
        List<Long> menuIds = new ArrayList<>();
        oldAuthentications.stream().forEach(item -> menuIds.add(item.getMenuId()));
        List<Authentication> createAuthentications = new ArrayList<>();
        list.stream().forEach(item -> {
            if (menuIds.contains(item.getMenuId())) {
                menuIds.remove(item.getMenuId());
            } else {
                item.setCreateTime(new Date());
                item.setModifyTime(new Date());
                item.setCreator(authenticationManager.getUserName());
                item.setModifier(authenticationManager.getUserName());
                createAuthentications.add(item);
            }
        });
        if (CollectionUtils.isNotEmpty(menuIds)) {
            batchDeleteByMenuId(menuIds);
        }
        if (CollectionUtils.isNotEmpty(createAuthentications)) {
            authenticationMapper.insertList(createAuthentications);
        }
    }

    /**
     * 根据角色id获取权限列表
     * @param roleId
     * @return
     */
    private List<Authentication> getByRoleId(Long roleId) {
        Example example = new Example(Authentication.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<Authentication> list = authenticationMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Long> getMenuIdByRoleId(Long roleId) {
        List<Authentication> list = getByRoleId(roleId);
        List<Long> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(item -> result.add(item.getMenuId()));
        }
        return result;
    }
}
