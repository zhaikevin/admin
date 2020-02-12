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
 * @Description:
 * @Author: kevin
 * @Date: 2020/2/11 12:19
 */
@Data
@Table(name = "sys_project")
public class Project implements Serializable {

    private static final long serialVersionUID = -3595915324254360772L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 项目名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 项目编码
     */
    @Column(name = "code")
    private String code;

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
