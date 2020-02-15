package com.github.admin.server.dao;

import com.github.admin.server.model.ProjectRel;
import com.github.admin.server.model.vo.ProjectRelVO;
import com.github.foundation.datasource.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:23
 */
@Repository
public interface ProjectRelMapper extends BaseMapper<ProjectRel> {

    /**
     * 分页查询
     * @param projectName
     * @param projectCode
     * @param groupId
     * @return
     */
    List<ProjectRelVO> queryByPage(String projectName, String projectCode, Long groupId);
}
