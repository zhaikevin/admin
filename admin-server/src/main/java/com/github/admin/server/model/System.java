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
 * @Date: 2020/1/6 15:01
 */
@Data
@Table(name = "sys_system")
public class System implements Serializable {

    private static final long serialVersionUID = -5549660969019353283L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * code
     */
    @Column(name = "code")
    private String code;

    /**
     * 系统名字
     */
    @Column(name = "name")
    private String name;

    /**
     * url
     */
    @Column(name = "url")
    private String url;

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
