package com.scan.pos.common.exception;

/**
 * 自定义业务异常
 * <p>
 * 所有业务异常统一使用状态码 500，通过全局异常处理器返回。
 * </p>
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
