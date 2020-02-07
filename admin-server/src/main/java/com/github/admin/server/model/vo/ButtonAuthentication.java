package com.github.admin.server.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: button鉴权
 * @Author: kevin
 * @Date: 2020/2/7 19:48
 */
@Data
public class ButtonAuthentication implements Serializable {

    private static final long serialVersionUID = 8050959293967023627L;

    /**
     * 菜单code
     */
    private String menuCode;

    /**
     * 权限标志
     */
    private Boolean flag;
}
