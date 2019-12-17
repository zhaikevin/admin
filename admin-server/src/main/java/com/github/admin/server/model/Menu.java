package com.github.admin.server.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 菜单
 * @Author: kevin
 * @Date: 2019/12/11 10:49
 */
@Data
@Table(name = "sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 166511052632741605L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 菜单code
     */
    @Column(name = "code")
    private String code;

    /**
     * url
     */
    @Column(name = "url")
    private String url;

    /**
     * 类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 排序序号
     */
    @Column(name = "sort_id")
    private Integer sortId;

    /**
     * 父菜单id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 是否有效
     */
    @Column(name = "is_valid")
    private Integer isValid;

    /**
     * 系统id
     */
    @Column(name = "system_id")
    private Long systemId;

    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "modifier")
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
}
