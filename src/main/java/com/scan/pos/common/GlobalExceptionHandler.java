package com.scan.pos.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * <p>
 * - BusinessException：统一返回状态码 500，message 透传异常信息
 * - 其他未捕获异常（500）：统一返回"服务器异常，请联系管理员"
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常，统一状态码 500
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    /**
     * 兜底处理其他所有未捕获异常（状态码 500）
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("服务器异常：", e);
        return Result.error("服务器异常，请联系管理员");
    }
}
