package com.github.admin.server.dao;

import com.github.admin.server.model.Menu;
import com.github.admin.server.model.vo.MenuTree;
import com.github.foundation.datasource.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/11 14:34
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取所有有效的菜单，不包括按钮
     * @return
     */
    List<MenuTree> getAllValidMenu();

    /**
     * 获取根目录
     * @return
     */
    List<MenuTree> getBaseMenu();

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
     * 获取所有
     * @return
     */
    List<MenuTree> getAll();
}
