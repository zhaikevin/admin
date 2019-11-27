package com.github.admin.server.config;

import com.github.foundation.common.exception.BaseRuntimeException;
import com.github.foundation.common.model.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandleAdvice {

    @ExceptionHandler
    @ResponseBody
    public ResultInfo handler(Exception e) {

        if (e instanceof BaseRuntimeException) {
            return ResultInfo.errorMessage(e.getMessage());
        }
        //兼容Preconditions.checkNotNull判断
        if (e instanceof NullPointerException && StringUtils.isNotEmpty(e.getMessage())) {
            return ResultInfo.errorMessage(e.getMessage());
        }
        log.error("Exception:{}", e.getMessage(), e); // 把漏网的异常信息记入日志
        return ResultInfo.errorMessage("服务器内部错误");
    }
}
