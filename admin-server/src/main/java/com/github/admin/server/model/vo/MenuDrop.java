package com.github.admin.server.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/2/10 16:03
 */
@Data
public class MenuDrop implements Serializable {

    private static final long serialVersionUID = -5890094972755121732L;

    /**
     * 拖拽的结点id
     */
    private Long draggingNodeId;

    /**
     * 目标结点id
     */
    private Long dropNodeId;

    /**
     * 类型
     */
    private String dropType;
}
