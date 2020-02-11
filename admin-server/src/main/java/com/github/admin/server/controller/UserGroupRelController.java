package com.github.admin.server.controller;

import com.github.admin.server.model.UserGroupRel;
import com.github.admin.server.service.UserGroupRelService;
import com.github.foundation.authentication.AuthenticationManager;
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

import java.util.Date;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/2/3 17:01
 */
@RestController
@RequestMapping("/userGroupRel")
public class UserGroupRelController {

    @Autowired
    private UserGroupRelService userGroupRelService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 保存用户和用户组的对应关系
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultInfo save(@RequestBody UserGroupRel userGroupRel) {
        userGroupRelService.save(userGroupRel.getUserId(), userGroupRel.getGroupId(), userGroupRel.getUserName());
        return ResultInfo.success();
    }

    /**
     * 修改用户和用户组的对应关系
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultInfo modify(@RequestBody UserGroupRel userGroupRel) {
        userGroupRel.setModifier(authenticationManager.getUserName());
        userGroupRel.setModifyTime(new Date());
        userGroupRelService.updateByPrimaryKeySelective(userGroupRel);
        return ResultInfo.success();
    }

    /**
     * 删除用户和用户组的对应关系
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
    public ResultInfo delete(@PathVariable(value = "id") Long id) {
        userGroupRelService.deleteByPrimaryKey(id);
        return ResultInfo.success();
    }


    /**
     * 获取列表
     * @param userName
     * @param groupId
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultInfo list(@RequestParam(value = "currentPage") Integer currentPage,
                           @RequestParam(value = "pageSize") Integer pageSize,
                           @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
                           @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                           @RequestParam(value = "userName", required = false) String userName,
                           @RequestParam(value = "groupId") Long groupId) {
        Sort sortObj = new Sort(new Order(Direction.fromString(order), sort));
        Pagination<UserGroupRel> pagination = new Pagination<>(currentPage, pageSize, sortObj);
        pagination.addParam(new SearchParams.Param("groupId", groupId, SearchParams.Compare.EQUAL));
        if (StringUtils.isNotBlank(userName)) {
            pagination.addParam(new SearchParams.Param("userName", userName, SearchParams.Compare.LIKE));
        }
        userGroupRelService.queryByPage(pagination);
        return ResultInfo.success(pagination);
    }

}
