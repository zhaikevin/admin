package com.github.admin.server.service.impl;

import com.github.admin.server.dao.ProjectMapper;
import com.github.admin.server.model.Project;
import com.github.admin.server.service.ProjectRelService;
import com.github.admin.server.service.ProjectService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.pagination.annotation.Pageable;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.service.BaseServiceImpl;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/19 10:15
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, ProjectMapper> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProjectRelService projectRelService;

    @Override
    @Transactional
    public void delete(Long id) {
        projectMapper.deleteByPrimaryKey(id);
        projectRelService.deleteByProjectId(id);
    }

    @Override
    public void create(Project project) {
        Example example = new Example(Project.class);
        example.createCriteria().andEqualTo("code", project.getCode());
        if (projectMapper.selectOneByExample(example) != null) {
            throw new BusinessException("项目编码不能重复");
        }
        project.setCreateTime(new Date());
        project.setCreator(authenticationManager.getUserName());
        project.setModifyTime(new Date());
        project.setModifier(authenticationManager.getUserName());
        projectMapper.insertUseGeneratedKeys(project);
    }

    @Override
    public void modify(Project project) {
        Example example = new Example(Project.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", project.getCode());
        criteria.andNotEqualTo("id", project.getId());
        if (projectMapper.selectOneByExample(example) != null) {
            throw new BusinessException("项目不能重复");
        }
        project.setModifier(authenticationManager.getUserName());
        project.setModifyTime(new Date());
        projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    @Pageable
    public void listByGroup(String projectName, String projectCode, Long groupId, Pagination pagination) {
        List<Project> list = projectMapper.listByGroup(projectName, projectCode, groupId);
        pagination.setDataset(list);
        pagination.setTotal(((Page<Project>) list).getTotal());
    }
}
