package com.github.admin.server.service;

import com.github.admin.server.dao.MenuMapper;
import com.github.admin.server.model.Menu;
import com.github.admin.server.model.vo.MenuTree;
import com.github.foundation.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/11 14:35
 */
public interface MenuService extends BaseService<Menu, MenuMapper> {

    /**
     * 根据用户获取菜单
     * @param userId
     * @return
     */
    List<MenuTree> getByParentId(Long userId, Long parentId);

    /**
     * 获取根目录
     * @param userId
     * @return
     */
    List<MenuTree> getBaseMenu(Long userId);

    /**
     * 根据id获取menuTree
     * @param menuId
     * @return
     */
    MenuTree getMenuTreeById(Long menuId);

    /**
     * 根据code获取menuTree
     * @param code
     * @return
     */
    MenuTree getMenuTreeByCode(String code);

    /**
     * 获取整个菜单树
     * @return
     */
    List<MenuTree> getAllMenu();

    /**
     * 创建菜单
     * @param menu
     */
    void create(Menu menu);

    /**
     * 修改菜单
     * @param menu
     */
    void modify(Menu menu);

    /**
     * 删除菜单
     * @param id
     */
    void delete(Long id);

    /**
     * 获取所有有效状态的菜单，不包括按钮
     * @return
     */
    List<Menu> getAllValidMenu();

    /**
     * 根据系统id获取菜单
     * @return
     */
    List<Menu> getBySystemId(Long systemId);
}
