package com.github.admin.server.service.impl;

import com.github.admin.server.dao.UserGroupRelMapper;
import com.github.admin.server.model.UserGroupRel;
import com.github.admin.server.model.vo.UserGroupRelVO;
import com.github.admin.server.service.UserGroupRelService;
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
public class UserGroupRelServiceImpl extends BaseServiceImpl<UserGroupRel, UserGroupRelMapper> implements UserGroupRelService {

    @Autowired
    private UserGroupRelMapper userGroupRelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void save(Long userId, Long groupId, String userName) {
        save(userId, groupId, userName, authenticationManager.getUserName());
    }

    @Override
    public void save(Long userId, Long groupId, String userName, String operator) {
        ValidateUtils.notEmptyString(userName, "用户名不能为空");
        ValidateUtils.notNull(userId, "用户id不能为空");
        ValidateUtils.notNull(groupId, "用户组id不能为空");
        ValidateUtils.notEmptyString(operator, "操作人不能为空");
        Example example = new Example(UserGroupRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("groupId", groupId);
        UserGroupRel userGroupRel = userGroupRelMapper.selectOneByExample(example);
        if (userGroupRel != null) {
            userGroupRel.setModifyTime(new Date());
            userGroupRel.setModifier(operator);
            userGroupRelMapper.updateByPrimaryKeySelective(userGroupRel);
        } else {
            userGroupRel = new UserGroupRel();
            userGroupRel.setUserId(userId);
            userGroupRel.setGroupId(groupId);
            userGroupRel.setUserName(userName);
            //默认都是非管理员
            userGroupRel.setAdminFlag(1);
            userGroupRel.setCreateTime(new Date());
            userGroupRel.setCreator(operator);
            userGroupRel.setModifyTime(new Date());
            userGroupRel.setModifier(operator);
            userGroupRelMapper.insertUseGeneratedKeys(userGroupRel);
        }
    }

    @Override
    public void delete(Long userId, Long groupId) {
        if (userId == null && groupId == null) {
            throw new BusinessException("用户id和用户组id不能同时为空");
        }
        Example example = new Example(UserGroupRel.class);
        Example.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andEqualTo("userId", userId);
        }
        if (groupId != null) {
            criteria.andEqualTo("groupId", groupId);
        }
        userGroupRelMapper.deleteByExample(example);
    }

    @Override
    public List<UserGroupRelVO> getByUserId(Long userId) {
        return userGroupRelMapper.getByUserId(userId);
    }
}
