package com.github.admin.server.service;

import com.github.admin.server.dao.ProjectRelMapper;
import com.github.admin.server.model.ProjectRel;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:14
 */
public interface ProjectRelService extends BaseService<ProjectRel, ProjectRelMapper> {

    /**
     * 保存用户组和项目之间的关系
     * @param projectRel
     */
    void save(ProjectRel projectRel);

    /**
     * 根据用户组id删除
     * @param groupId
     */
    void deleteByGroupId(Long groupId);

    /**
     * 根据项目id删除
     * @param projectId
     */
    void deleteByProjectId(Long projectId);

    /**
     * 分页查询
     * @param pagination
     * @param projectName
     * @param projectCode
     * @param groupId
     * @return
     */
    void queryByPage(String projectName, String projectCode, Long groupId, Pagination pagination);
}
