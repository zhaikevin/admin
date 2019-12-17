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
     * 获取所有
     * @return
     */
    List<MenuTree> getAll();

    /**
     * 获取根目录
     * @return
     */
    List<MenuTree> getBaseMenu();
}
