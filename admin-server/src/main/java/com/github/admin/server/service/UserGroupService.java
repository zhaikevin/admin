package com.github.admin.server.service;

import com.github.admin.server.dao.UserGroupMapper;
import com.github.admin.server.model.UserGroup;
import com.github.foundation.service.BaseService;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface UserGroupService extends BaseService<UserGroup, UserGroupMapper> {

    /**
     * 删除，同时把用户组和用户的关联关系删除，关联项目删除
     * @param id
     */
    void delete(Long id);

    /**
     * 创建角色
     * @param userGroup
     */
    void create(UserGroup userGroup);

    /**
     * 修改角色
     * @param userGroup
     */
    void modify(UserGroup userGroup);

    /**
     * 根据code获取
     * @param code
     */
    UserGroup getByCode(String code);
}
