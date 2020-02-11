package com.github.admin.server.service;

import com.github.admin.server.dao.UserGroupRelMapper;
import com.github.admin.server.model.UserGroupRel;
import com.github.admin.server.model.vo.UserGroupRelVO;
import com.github.foundation.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface UserGroupRelService extends BaseService<UserGroupRel, UserGroupRelMapper> {


    /**
     * 保存用户和用户组之间的关系
     * @param userId
     * @param groupId
     */
    void save(Long userId, Long groupId, String userName);

    /**
     * 保存用户和用户组之间的关系
     * @param userId
     * @param groupId
     * @param userName
     * @param operator
     */
    void save(Long userId, Long groupId, String userName, String operator);

    /**
     * 删除用户和用户组之间的关系，两个参数不能都为空
     * @param userId
     * @param groupId
     */
    void delete(Long userId, Long groupId);

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<UserGroupRelVO> getByUserId(Long userId);
}
