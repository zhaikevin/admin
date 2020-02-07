package com.github.admin.server.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/2/6 21:39
 */
@Data
public class AuthenticationVO implements Serializable {

    private static final long serialVersionUID = -6384024908816999428L;

    /**
     * 权限列表
     */
    private String authentications;

    /**
     * 角色id
     */
    private Long roleId;
}
