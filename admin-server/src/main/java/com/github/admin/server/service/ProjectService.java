package com.github.admin.server.service;

import com.github.admin.server.dao.ProjectMapper;
import com.github.admin.server.model.Project;
import com.github.admin.server.model.UserGroup;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.service.BaseService;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface ProjectService extends BaseService<Project, ProjectMapper> {

    /**
     * 删除，同时把用户组和项目的关联关系删除
     * @param id
     */
    void delete(Long id);

    /**
     * 创建项目
     * @param project
     */
    void create(Project project);

    /**
     * 修改角色
     * @param project
     */
    void modify(Project project);

    /**
     *
     * @param projectName
     * @param projectCode
     * @param groupId
     * @param pagination
     */
    void listByGroup(String projectName, String projectCode, Long groupId, Pagination pagination);

}
