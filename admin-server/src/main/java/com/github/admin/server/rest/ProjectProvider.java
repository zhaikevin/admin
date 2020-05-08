package com.github.admin.server.rest;

import com.github.admin.server.service.ProjectService;
import com.github.foundation.common.model.ResultInfo;
import com.github.foundation.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/5/7 11:51
 */
@RequestMapping("/project")
@RestController
public class ProjectProvider {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResultInfo all() {
        return ResultInfo.success(JsonUtils.toJson(projectService.getAll()));
    }
}
