package com.github.admin.server.service.impl;

import com.github.admin.server.constant.CommonState;
import com.github.admin.server.dao.SysUserMapper;
import com.github.admin.server.model.SysUser;
import com.github.admin.server.service.SysUserService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.common.utils.DateUtils;
import com.github.foundation.common.utils.RandomUtils;
import com.github.foundation.common.utils.ValidateUtils;
import com.github.foundation.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    @Override
    public SysUser getByName(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", userName);
        return sysUserMapper.selectOneByExample(example);
    }

    @Override
    public Long register(SysUser sysUser) {
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
        sysUser.setState(CommonState.VALID.getVal());
        sysUser.setCreator(sysUser.getUsername());
        sysUser.setModifier(sysUser.getUsername());
        sysUser.setCreateTime(DateUtils.now());
        sysUser.setModifyTime(DateUtils.now());
        sysUserMapper.insertUseGeneratedKeys(sysUser);
        return sysUser.getId();
    }
}
