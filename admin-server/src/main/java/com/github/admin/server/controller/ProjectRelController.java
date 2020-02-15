package com.github.admin.server.controller;

import com.github.admin.server.model.ProjectRel;
import com.github.admin.server.model.vo.ProjectRelVO;
import com.github.admin.server.service.ProjectRelService;
import com.github.foundation.common.model.ResultInfo;
import com.github.foundation.pagination.model.Direction;
import com.github.foundation.pagination.model.Order;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.pagination.model.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/2/3 17:01
 */
@RestController
@RequestMapping("/projectRel")
public class ProjectRelController {

    @Autowired
    private ProjectRelService projectRelService;

    /**
     * 保存用户组和项目的对应关系
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultInfo save(@RequestBody ProjectRel projectRel) {
        projectRelService.save(projectRel);
        return ResultInfo.success();
    }

    /**
     * 删除用户组和项目的对应关系
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
    public ResultInfo delete(@PathVariable(value = "id") Long id) {
        projectRelService.deleteByPrimaryKey(id);
        return ResultInfo.success();
    }

    /**
     * 获取列表
     * @param currentPage
     * @param pageSize
     * @param sort
     * @param order
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultInfo list(@RequestParam(value = "currentPage") Integer currentPage,
                           @RequestParam(value = "pageSize") Integer pageSize,
                           @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
                           @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                           @RequestParam(value = "projectName", required = false) String projectName,
                           @RequestParam(value = "projectCode", required = false) String projectCode,
                           @RequestParam(value = "groupId") Long groupId) {
        Sort sortObj = new Sort(new Order(Direction.fromString(order), sort));
        Pagination<ProjectRelVO> pagination = new Pagination<>(currentPage, pageSize, sortObj);
        projectRelService.queryByPage(projectName, projectCode, groupId, pagination);
        return ResultInfo.success(pagination);
    }

}
