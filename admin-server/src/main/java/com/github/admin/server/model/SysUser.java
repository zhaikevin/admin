package com.github.admin.server.model;

import com.github.foundation.service.annotations.ExcludeByPage;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: 系统用户
 * @Author: kevin
 * @Date: 2019/11/25 10:44
 */
@Data
@Table(name = "sys_user")
public class SysUser {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    @ExcludeByPage
    private String password;

    /**
     * 盐
     */
    @Column(name = "salt")
    @ExcludeByPage
    private String salt;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    @Column(name = "state")
    private Integer state;

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

}
