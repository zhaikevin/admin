package com.github.admin.server.service.impl;

import com.github.admin.server.dao.ProjectRelMapper;
import com.github.admin.server.model.ProjectRel;
import com.github.admin.server.model.vo.ProjectRelVO;
import com.github.admin.server.service.ProjectRelService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.utils.ValidateUtils;
import com.github.foundation.pagination.annotation.Pageable;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.service.BaseServiceImpl;
import com.github.pagehelper.Page;
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
public class ProjectRelServiceImpl extends BaseServiceImpl<ProjectRel, ProjectRelMapper> implements ProjectRelService {

    @Autowired
    private ProjectRelMapper projectRelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void save(ProjectRel projectRel) {
        ValidateUtils.notNull(projectRel.getGroupId(), "用户组id不能为空");
        ValidateUtils.notNull(projectRel.getProjectId(), "项目id不能为空");
        Example example = new Example(ProjectRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId", projectRel.getGroupId());
        criteria.andEqualTo("projectId", projectRel.getProjectId());
        ProjectRel oldProjectRel = projectRelMapper.selectOneByExample(example);
        if (oldProjectRel != null) {
            oldProjectRel.setModifyTime(new Date());
            oldProjectRel.setModifier(authenticationManager.getUserName());
            projectRelMapper.updateByPrimaryKeySelective(oldProjectRel);
        } else {
            projectRel.setCreateTime(new Date());
            projectRel.setCreator(authenticationManager.getUserName());
            projectRel.setModifyTime(new Date());
            projectRel.setModifier(authenticationManager.getUserName());
            projectRelMapper.insertUseGeneratedKeys(projectRel);
        }
    }

    @Override
    public void deleteByGroupId(Long groupId) {
        Example example = new Example(ProjectRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId", groupId);
        projectRelMapper.deleteByExample(example);
    }

    @Override
    public void deleteByProjectId(Long projectId) {
        Example example = new Example(ProjectRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("projectId", projectId);
        projectRelMapper.deleteByExample(example);
    }

    @Override
    @Pageable
    public void queryByPage(String projectName, String projectCode, Long groupId, Pagination pagination) {
        List<ProjectRelVO> list = projectRelMapper.queryByPage(projectName, projectCode, groupId);
        pagination.setDataset(list);
        pagination.setTotal(((Page<ProjectRelVO>) list).getTotal());
    }
}
