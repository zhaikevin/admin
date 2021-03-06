package com.github.admin.server.service;

import com.github.admin.server.dao.UserRoleMapper;
import com.github.admin.server.model.UserRole;
import com.github.foundation.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface UserRoleService extends BaseService<UserRole, UserRoleMapper> {

    /**
     * 保存用户和角色之间的关系
     * @param userId
     * @param roleId
     */
    void save(Long userId, Long roleId, String userName);

    /**
     * 保存用户和角色之间的关系
     * @param userId
     * @param roleId
     * @param userName
     * @param operator
     */
    void save(Long userId, Long roleId, String userName, String operator);

    /**
     * 删除用户和角色之间的关系，两个参数不能都为空
     * @param userId
     * @param roleId
     */
    void delete(Long userId, Long roleId);

    /**
     * 根据用户id获取
     * @param userId
     */
    List<UserRole> getByUserId(Long userId);
}
