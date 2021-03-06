package com.github.admin.server.dao;

import com.github.admin.server.model.Project;
import com.github.foundation.datasource.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:23
 */
@Repository
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * @param projectName
     * @param projectCode
     * @param groupId
     */
    List<Project> listByGroup(String projectName, String projectCode, Long groupId);

}
