package com.github.admin.server.service.impl;

import com.github.admin.server.dao.MenuMapper;
import com.github.admin.server.model.Menu;
import com.github.admin.server.model.vo.MenuTree;
import com.github.admin.server.service.MenuService;
import com.github.foundation.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/12 11:00
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuTree> getByParentId(Long userId, Long parentId) {
        List<MenuTree> list = menuMapper.getAll();
        MenuTree baseMenu = convert(getById(parentId));
        getSubMenuList(baseMenu, list, userId);
        return baseMenu.getChildren();
    }

    @Override
    public List<MenuTree> getBaseMenu(Long userId) {
        return menuMapper.getBaseMenu();
    }

    @Override
    public MenuTree getMenuTreeById(Long menuId) {
        return menuMapper.getMenuTreeById(menuId);
    }

    /**
     * 获取子菜单列表
     * @param parentMenuTree 父菜单
     * @param list           菜单列表
     * @param userId         用户id
     */
    private void getSubMenuList(MenuTree parentMenuTree, List<MenuTree> list, Long userId) {
        for (MenuTree menuTree : list) {
            //TODO 后续加上用户权限判断
            if (menuTree.getParentId().equals(parentMenuTree.getId())) {
                parentMenuTree.addChild(menuTree);
                getSubMenuList(menuTree, list, userId);
            }
        }
    }

    /**
     * 转化
     * @param menu
     * @return
     */
    private MenuTree convert(Menu menu) {
        MenuTree menuTree = new MenuTree();
        menuTree.setId(menu.getId());
        menuTree.setCode(menu.getCode());
        menuTree.setName(menu.getName());
        menuTree.setUrl(menu.getUrl());
        return menuTree;
    }
}
