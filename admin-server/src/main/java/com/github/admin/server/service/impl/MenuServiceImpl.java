package com.github.admin.server.service.impl;

import com.github.admin.server.constant.CommonState;
import com.github.admin.server.constant.MenuType;
import com.github.admin.server.dao.MenuMapper;
import com.github.admin.server.model.Menu;
import com.github.admin.server.model.vo.ButtonAuthentication;
import com.github.admin.server.model.vo.MenuTree;
import com.github.admin.server.service.AuthenticationService;
import com.github.admin.server.service.MenuService;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.exception.BusinessException;
import com.github.foundation.service.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/12 11:00
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuMapper> implements MenuService {

    //根节点的父节点为0
    private static final Long ROOT_PARENT_ID = 0L;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<MenuTree> getByParentId(Long userId, Long parentId) {
        List<MenuTree> list = menuMapper.getAllValidMenu();
        MenuTree baseMenu = convert(getById(parentId));
        getSubMenuList(baseMenu, list, authenticationService.getAuthentication(userId));
        return baseMenu.getChildren();
    }

    @Override
    public List<MenuTree> getBaseMenu(Long userId) {
        Map<Long, Long> map = authenticationService.getAuthentication(userId);
        return menuMapper.getBaseMenu().stream().filter(item -> map.containsKey(item.getId())).collect(Collectors.toList());
    }

    @Override
    public MenuTree getMenuTreeById(Long menuId) {
        return menuMapper.getMenuTreeById(menuId);
    }

    @Override
    public MenuTree getMenuTreeByCode(String code) {
        return menuMapper.getMenuTreeByCode(code);
    }

    @Override
    public List<MenuTree> getAllMenu(Integer state) {
        List<MenuTree> list = menuMapper.getAll(state);
        List<MenuTree> result = new ArrayList<>();
        for (MenuTree menuTree : list) {
            //父节点
            if (menuTree.getParentId().equals(ROOT_PARENT_ID)) {
                result.add(menuTree);
                getSubMenuList(menuTree, list);
            }
        }
        return result;
    }

    @Override
    public void create(Menu menu) {
        Example example = new Example(Menu.class);
        example.createCriteria().andEqualTo("code", menu.getCode());
        if (menuMapper.selectOneByExample(example) != null) {
            throw new BusinessException("菜单编码不能重复");
        }
        menu.setCreator(authenticationManager.getUserName());
        menu.setCreateTime(new Date());
        menu.setModifier(authenticationManager.getUserName());
        menu.setModifyTime(new Date());
        menuMapper.insertUseGeneratedKeys(menu);
    }

    @Override
    public void modify(Menu menu) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", menu.getCode());
        criteria.andNotEqualTo("id", menu.getId());
        if (menuMapper.selectOneByExample(example) != null) {
            throw new BusinessException("菜单编码不能重复");
        }
        menu.setModifier(authenticationManager.getUserName());
        menu.setModifyTime(new Date());
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", id);
        List<MenuTree> list = menuMapper.getAllValidMenu();
        MenuTree baseMenu = convert(getById(id));
        getSubMenuList(baseMenu, list);
        List<MenuTree> menuTreeList = baseMenu.getChildren();
        List<Long> idList = new ArrayList<>();
        idList.add(id);
        if (CollectionUtils.isNotEmpty(menuTreeList)) {
            menuTreeList.stream().forEach(item -> {
                idList.add(item.getId());
            });
        }
        menuMapper.deleteByPrimaryKey(id);
        menuMapper.deleteByExample(example);
        authenticationService.batchDeleteByMenuId(idList);
    }

    @Override
    public List<Menu> getAllValidMenu() {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isValid", CommonState.VALID.getVal());
        criteria.andEqualTo("type", MenuType.MENU.getVal());
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<Menu> getBySystemId(Long systemId) {
        Example example = new Example(Menu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("systemId", systemId);
        return menuMapper.selectByExample(example);
    }

    @Override
    public void buttonAuthentication(List<ButtonAuthentication> list) {
        Map<Long, Long> map = authenticationService.getAuthentication(authenticationManager.getUserId());
        for (ButtonAuthentication authentication : list) {
            MenuTree menuTree = getMenuTreeByCode(authentication.getMenuCode());
            if (menuTree == null) {
                authentication.setFlag(false);
            } else {
                authentication.setFlag(map.containsKey(menuTree.getId()));
            }
        }
    }

    /**
     * 获取子菜单列表
     * @param parentMenuTree 父菜单
     * @param list           菜单列表
     * @param map            用户有权限的菜单map
     */
    private void getSubMenuList(MenuTree parentMenuTree, List<MenuTree> list, Map<Long, Long> map) {
        for (MenuTree menuTree : list) {
            //TODO 后续加上用户权限判断
            if (menuTree.getParentId().equals(parentMenuTree.getId()) && map.containsKey(menuTree.getId())) {
                parentMenuTree.addChild(menuTree);
                getSubMenuList(menuTree, list, map);
            }
        }
    }

    /**
     * 获取子菜单，不加权限控制
     * @param parentMenuTree
     * @param list
     */
    private void getSubMenuList(MenuTree parentMenuTree, List<MenuTree> list) {
        for (MenuTree menuTree : list) {
            if (menuTree.getParentId().equals(parentMenuTree.getId())) {
                parentMenuTree.addChild(menuTree);
                getSubMenuList(menuTree, list);
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
