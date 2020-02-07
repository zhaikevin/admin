package com.github.admin.server.controller;

import com.github.admin.server.model.Menu;
import com.github.admin.server.model.vo.ButtonAuthentication;
import com.github.admin.server.service.MenuService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.model.ResultInfo;
import com.github.foundation.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MenuService menuService;

    /**
     * 根据用户获取菜单，左侧目录
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/getByParentId", method = RequestMethod.GET)
    public ResultInfo getByParentId(@RequestParam(value = "parentId") Long parentId) {
        return ResultInfo.success(menuService.getByParentId(authenticationManager.getUserId(), parentId));
    }

    /**
     * 获取跟目录，顶层目录
     */
    @RequestMapping(value = "/getBaseMenu", method = RequestMethod.GET)
    public ResultInfo getBaseMenu() {
        return ResultInfo.success(menuService.getBaseMenu(authenticationManager.getUserId()));
    }

    /**
     * 根据id获取菜单详情，返回对象是menu
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public ResultInfo getById(@RequestParam(value = "menuId") Long menuId) {
        return ResultInfo.success(menuService.getById(menuId));
    }

    /**
     * 根据code获取菜单，返回对象是menuTree
     * @param code
     * @return
     */
    @RequestMapping(value = "/getByCode", method = RequestMethod.GET)
    public ResultInfo getById(@RequestParam(value = "code") String code) {
        return ResultInfo.success(menuService.getMenuTreeByCode(code));
    }

    /**
     * 获取整个菜单树，包括菜单、按钮
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultInfo getAll(@RequestParam(value = "state", required = false) Integer state) {
        return ResultInfo.success(menuService.getAllMenu(state));
    }

    /**
     * 创建菜单
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResultInfo create(@RequestBody Menu menu) {
        menuService.create(menu);
        return ResultInfo.success();
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultInfo modify(@RequestBody Menu menu) {
        menuService.modify(menu);
        return ResultInfo.success();
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
    public ResultInfo delete(@PathVariable(value = "id") Long id) {
        menuService.delete(id);
        return ResultInfo.success();
    }

    /**
     * 获取所有有效的菜单，不包括按钮
     * @return
     */
    @RequestMapping(value = "/getAllValidMenu", method = RequestMethod.GET)
    public ResultInfo getAllValidMenu() {
        return ResultInfo.success(menuService.getAllValidMenu());
    }

    /**
     * button鉴权
     * @param buttons
     * @return
     */
    @RequestMapping(value = "/buttonAuthentication", method = RequestMethod.GET)
    public ResultInfo buttonAuthentication(@RequestParam(value = "buttons") String buttons) {
        List<ButtonAuthentication> list = JsonUtils.listFromJson(buttons, ButtonAuthentication.class);
        menuService.buttonAuthentication(list);
        return ResultInfo.success(list);
    }

}
