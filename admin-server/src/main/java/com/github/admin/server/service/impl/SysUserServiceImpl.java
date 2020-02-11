package com.github.admin.server.service.impl;

import com.github.admin.server.constant.CommonState;
import com.github.admin.server.dao.SysUserMapper;
import com.github.admin.server.model.Role;
import com.github.admin.server.model.SysUser;
import com.github.admin.server.model.UserGroup;
import com.github.admin.server.service.RoleService;
import com.github.admin.server.service.SysUserService;
import com.github.admin.server.service.UserGroupRelService;
import com.github.admin.server.service.UserGroupService;
import com.github.admin.server.service.UserRoleService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.common.utils.DateUtils;
import com.github.foundation.common.utils.RandomUtils;
import com.github.foundation.common.utils.ValidateUtils;
import com.github.foundation.pagination.annotation.Pageable;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.service.BaseServiceImpl;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:29
 */
@Service
@Slf4j
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, SysUserMapper> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserGroupRelService userGroupRelService;

    @Autowired
    private UserGroupService userGroupService;

    @Value("${foundation.user.defaultRoleCode}")
    private String defaultRoleCode;

    @Value("${foundation.user.defaultGroupCode}")
    private String defaultGroupCode;

    @Override
    public SysUser getByName(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", userName);
        return sysUserMapper.selectOneByExample(example);
    }

    @Override
    @Transactional
    public Long create(SysUser sysUser, String operation) {
        ValidateUtils.notEmptyString(sysUser.getUsername(), "用户名不能为空");
        ValidateUtils.notEmptyString(sysUser.getPassword(), "密码不能为空");
        ValidateUtils.notEmptyString(sysUser.getEmail(), "邮箱不能为空");
        if (getByName(sysUser.getUsername()) != null) {
            throw new BusinessException("用户名已被注册");
        }
        String salt = RandomUtils.randomGen(8);
        sysUser.setSalt(salt);
        try {
            sysUser.setPassword(authenticationManager.encrypt(sysUser.getUsername(), sysUser.getPassword(), salt));
        } catch (Exception e) {
            log.error("encrypt password failed:{}", e.getMessage(), e);
            throw new BusinessException("注册用户失败");
        }
        if (operation.equals("register")) {
            sysUser.setState(CommonState.VALID.getVal());
            sysUser.setCreator(sysUser.getUsername());
            sysUser.setModifier(sysUser.getUsername());
        } else {
            sysUser.setCreator(authenticationManager.getUserName());
            sysUser.setModifier(authenticationManager.getUserName());
        }
        sysUser.setCreateTime(DateUtils.now());
        sysUser.setModifyTime(DateUtils.now());
        //新建用户
        sysUserMapper.insertUseGeneratedKeys(sysUser);

        //把用户加入到默认角色
        Role role = roleService.getByCode(defaultRoleCode);
        if (role != null) {
            if (operation.equals("register")) {
                userRoleService.save(sysUser.getId(), role.getId(), sysUser.getUsername(), sysUser.getUsername());
            } else {
                userRoleService.save(sysUser.getId(), role.getId(), sysUser.getUsername());
            }
        }

        //把用户加入到默认分组
        UserGroup userGroup = userGroupService.getByCode(defaultGroupCode);
        if (userGroup != null) {
            if (operation.equals("register")) {
                userGroupRelService.save(sysUser.getId(), userGroup.getId(), sysUser.getUsername(), sysUser.getUsername());
            } else {
                userGroupRelService.save(sysUser.getId(), userGroup.getId(), sysUser.getUsername());
            }
        }
        return sysUser.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sysUserMapper.deleteByPrimaryKey(id);
        userRoleService.delete(id, null);
        userGroupRelService.delete(id, null);
    }

    @Override
    public void resetPassword(SysUser sysUser) {
        SysUser userInfo = sysUserMapper.selectByPrimaryKey(sysUser.getId());
        sysUser.setModifyTime(new Date());
        sysUser.setModifier(authenticationManager.getUserName());
        try {
            sysUser.setPassword(authenticationManager.encrypt(userInfo.getUsername(), sysUser.getPassword(), userInfo.getSalt()));
        } catch (Exception e) {
            log.error("encrypt password failed:{}", e.getMessage(), e);
            throw new BusinessException("重置密码失败");
        }
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    @Pageable
    public void listByRole(Long roleId, String username, Pagination<SysUser> pagination) {
        List<SysUser> list = sysUserMapper.listByRole(roleId, username);
        pagination.setDataset(list);
        pagination.setTotal(((Page<SysUser>) list).getTotal());
    }

    @Override
    @Pageable
    public void listByGroup(Long groupId, String username, Pagination<SysUser> pagination) {
        List<SysUser> list = sysUserMapper.listByGroup(groupId, username);
        pagination.setDataset(list);
        pagination.setTotal(((Page<SysUser>) list).getTotal());
    }

    @Override
    public SysUser getById(Long id) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        example.excludeProperties("salt");
        example.excludeProperties("password");
        return sysUserMapper.selectOneByExample(example);
    }

}
