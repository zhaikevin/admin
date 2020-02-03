package com.github.admin.server.service.impl;

import com.github.admin.server.constant.CommonState;
import com.github.admin.server.dao.SysUserMapper;
import com.github.admin.server.model.SysUser;
import com.github.admin.server.service.SysUserService;
import com.github.admin.server.service.UserRoleService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.common.utils.DateUtils;
import com.github.foundation.common.utils.RandomUtils;
import com.github.foundation.common.utils.ValidateUtils;
import com.github.foundation.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

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

    @Override
    public SysUser getByName(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", userName);
        return sysUserMapper.selectOneByExample(example);
    }

    @Override
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
        sysUserMapper.insertUseGeneratedKeys(sysUser);
        return sysUser.getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sysUserMapper.deleteByPrimaryKey(id);
        userRoleService.delete(id, null);
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
    public SysUser getById(Long id) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        example.excludeProperties("salt");
        example.excludeProperties("password");
        return sysUserMapper.selectOneByExample(example);
    }

}
