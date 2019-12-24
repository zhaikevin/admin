package com.github.admin.server.controller;

import com.github.admin.server.service.MenuService;
import com.github.foundation.authentication.AuthenticationManager;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MenuService menuService;

    /**
     * 根据用户获取菜单
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/getByParentId", method = RequestMethod.GET)
    public ResultInfo getByParentId(@RequestParam(value = "parentId") Long parentId) {
        return ResultInfo.success(menuService.getByParentId(authenticationManager.getUserId(), parentId));
    }

    /**
     * 获取跟目录
     */
    @RequestMapping(value = "/getBaseMenu", method = RequestMethod.GET)
    public ResultInfo getBaseMenu() {
        return ResultInfo.success(menuService.getBaseMenu(authenticationManager.getUserId()));
    }

    /**
     * 根据id获取菜单
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResultInfo getById(@RequestParam(value = "menuId") Long menuId) {
        return ResultInfo.success(menuService.getMenuTreeById(menuId));
    }

    /**
     * 根据code获取菜单
     * @param code
     * @return
     */
    @RequestMapping(value = "/getByCode", method = RequestMethod.GET)
    public ResultInfo getById(@RequestParam(value = "code") String code) {
        return ResultInfo.success(menuService.getMenuTreeByCode(code));
    }
}
