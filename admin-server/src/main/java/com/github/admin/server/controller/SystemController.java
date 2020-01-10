package com.github.admin.server.controller;

import com.github.admin.server.service.SystemService;
import com.github.foundation.common.model.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/7 15:05
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * 获取所有
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResultInfo getAll() {
        return ResultInfo.success(systemService.getAll());
    }
}
