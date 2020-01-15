package com.github.admin.server.controller;

import com.github.admin.server.model.SysUser;
import com.github.admin.server.service.SysUserService;
import com.github.foundation.common.model.ResultInfo;
import com.github.foundation.pagination.model.Direction;
import com.github.foundation.pagination.model.Order;
import com.github.foundation.pagination.model.Pagination;
import com.github.foundation.pagination.model.SearchParams;
import com.github.foundation.pagination.model.Sort;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 系统用户控制器
 * @Author: kevin
 * @Date: 2019/11/26 10:35
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param sort
     * @param order
     * @param state
     * @param username
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultInfo list(@RequestParam(value = "currentPage") Integer currentPage,
                           @RequestParam(value = "pageSize") Integer pageSize,
                           @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
                           @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                           @RequestParam(value = "state", required = false) Integer state,
                           @RequestParam(value = "username", required = false) String username) {
        Sort sortObj = new Sort(new Order(Direction.fromString(order), sort));
        Pagination<SysUser> pagination = new Pagination<>(currentPage, pageSize, sortObj);
        if (state != null) {
            pagination.addParam(new SearchParams.Param("state", state, SearchParams.Compare.EQUAL));
        }
        if (StringUtils.isNotBlank(username)) {
            pagination.addParam(new SearchParams.Param("username", username, SearchParams.Compare.LIKE));
        }
        sysUserService.queryByPage(pagination);
        return ResultInfo.success(pagination);
    }

    /**
     * 创建用户
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultInfo create(@RequestBody SysUser user) {
        sysUserService.create(user,"create");
        return ResultInfo.success();
    }

}
