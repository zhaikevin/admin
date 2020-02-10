package com.github.admin.server.service;

import com.github.admin.server.dao.SystemMapper;
import com.github.admin.server.model.System;
import com.github.foundation.service.BaseService;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/7 14:47
 */
public interface SystemService extends BaseService<System, SystemMapper> {

    /**
     * 根据id删除
     * @param id
     */
    void delete(Long id);

    /**
     * 创建
     * @param system
     */
    void create(System system);

    /**
     * 修改
     * @param system
     */
    void update(System system);
}
