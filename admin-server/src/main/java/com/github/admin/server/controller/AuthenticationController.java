package com.github.admin.server.controller;

import com.github.admin.server.model.Authentication;
import com.github.admin.server.model.vo.AuthenticationVO;
import com.github.admin.server.service.AuthenticationService;
import com.github.foundation.common.model.ResultInfo;
import com.github.foundation.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @RequestMapping(value = "/getMenuId", method = RequestMethod.GET)
    public ResultInfo getMenuId(@RequestParam(value = "roleId") Long roleId) {
        return ResultInfo.success(authenticationService.getMenuIdByRoleId(roleId));
    }

    /**
     * 保存权限
     * @param authentications
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultInfo save(@RequestBody AuthenticationVO authentications) {
        List<Authentication> list = JsonUtils.listFromJson(authentications.getAuthentications(), Authentication.class);
        authenticationService.save(list, authentications.getRoleId());
        return ResultInfo.success();
    }
}
