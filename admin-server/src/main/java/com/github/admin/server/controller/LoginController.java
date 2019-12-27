package com.github.admin.server.controller;

import com.github.admin.server.constant.Consts;
import com.github.admin.server.model.SysUser;
import com.github.admin.server.service.SysUserService;
import com.github.admin.server.utils.WebUtils;
import com.github.foundation.authentication.AuthenticationManager;
import com.github.foundation.common.model.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 登录控制器
 * @Author: kevin
 * @Date: 2019/11/25 14:28
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo login(@RequestBody SysUser user, HttpServletResponse response) {
        try {
            authenticationManager.login(user.getUsername(), user.getPassword());
            //登录成功后加入cookie信息
            WebUtils.addCookie(Consts.COOKIE_USER_NAME, user.getUsername(), response);
        } catch (AuthenticationException e) {
            log.error("login failed:{}", e.getMessage(), e);
            return ResultInfo.errorMessage(e.getMessage());
        }
        return ResultInfo.success();
    }


    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResultInfo logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationManager.logout();
        WebUtils.clearCookie(request, response);
        return ResultInfo.success();
    }

    /**
     * 注册用户
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultInfo register(@RequestBody SysUser user) {
        sysUserService.register(user);
        return ResultInfo.success();
    }
}
