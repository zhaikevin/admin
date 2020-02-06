package com.github.admin.server.controller;

import com.github.admin.server.service.AuthenticationService;
import com.github.foundation.common.model.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/11 14:32
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 根据角色id获取菜单code列表
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/getMenuCode", method = RequestMethod.GET)
    public ResultInfo getMenuCode(@RequestParam(value = "roleId") Long roleId) {
        return ResultInfo.success(authenticationService.getMenuCodeByRoleId(roleId));
    }
}
