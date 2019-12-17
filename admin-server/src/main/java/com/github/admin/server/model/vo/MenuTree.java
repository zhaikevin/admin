package com.github.admin.server.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/12/12 11:13
 */
@Data
public class MenuTree implements Serializable {

    private static final long serialVersionUID = 4445466470004248086L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 菜单名字
     */
    private String name;

    /**
     * 菜单code
     */
    private String code;

    /**
     * url
     */
    private String url;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 子节点
     */
    private List<MenuTree> children = new ArrayList<>();

    public void addChild(MenuTree menuTree) {
        children.add(menuTree);
    }
}
