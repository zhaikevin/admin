package com.github.admin.server.controller;

import com.github.admin.server.model.UserGroup;
import com.github.admin.server.service.UserGroupService;
import com.github.foundation.common.model.ResultInfo;
import com.github.foundation.pagination.model.Direction;
import com.github.foundation.pagination.model.Order;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.pagination.model.SearchParams;
import com.github.foundation.pagination.model.Sort;
import org.apache.commons.lang.StringUtils;
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
 * @Date: 2020/1/19 10:09
 */
@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    /**
     * @param currentPage
     * @param pageSize
     * @param sort
     * @param order
     * @param name
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultInfo list(@RequestParam(value = "currentPage") Integer currentPage,
                           @RequestParam(value = "pageSize") Integer pageSize,
                           @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
                           @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                           @RequestParam(value = "name", required = false) String name) {
        Sort sortObj = new Sort(new Order(Direction.fromString(order), sort));
        Pagination<UserGroup> pagination = new Pagination<>(currentPage, pageSize, sortObj);
        if (StringUtils.isNotBlank(name)) {
            pagination.addParam(new SearchParams.Param("name", name, SearchParams.Compare.LIKE));
        }
        userGroupService.queryByPage(pagination);
        return ResultInfo.success(pagination);
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultInfo create(@RequestBody UserGroup userGroup) {
        userGroupService.create(userGroup);
        return ResultInfo.success();
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResultInfo getById(@RequestParam(value = "id") Long id) {
        return ResultInfo.success(userGroupService.getById(id));
    }

    /**
     * 修改
     * @param
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultInfo modify(@RequestBody UserGroup userGroup) {
        userGroupService.modify(userGroup);
        return ResultInfo.success();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
    public ResultInfo delete(@PathVariable(value = "id") Long id) {
        userGroupService.delete(id);
        return ResultInfo.success();
    }
}
