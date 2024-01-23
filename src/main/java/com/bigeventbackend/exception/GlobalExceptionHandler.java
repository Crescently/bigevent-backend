package com.bigeventbackend.exception;

import com.bigeventbackend.common.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception exception) {
        exception.printStackTrace();
        return Result.error(StringUtils.hasLength(exception.getMessage()) ? exception.getMessage() : "操作失败");

    }
}
