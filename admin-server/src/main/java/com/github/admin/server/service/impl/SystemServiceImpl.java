package com.github.admin.server.service.impl;

import com.github.admin.server.dao.SystemMapper;
import com.github.admin.server.model.System;
import com.github.admin.server.service.SystemService;
import com.github.foundation.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2020/1/7 14:48
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<System, SystemMapper> implements SystemService {
}
