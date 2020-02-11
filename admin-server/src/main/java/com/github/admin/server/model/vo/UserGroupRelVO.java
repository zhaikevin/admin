package com.github.admin.server.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/2/11 21:46
 */
@Data
public class UserGroupRelVO implements Serializable {

    private static final long serialVersionUID = 8462616969339685196L;

    private Long groupId;

    private String groupCode;

    private Integer adminFlag;

}
