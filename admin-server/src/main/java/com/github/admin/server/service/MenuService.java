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
}
