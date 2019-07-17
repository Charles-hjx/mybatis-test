package com.hjx.springbootmybatis.common.exception;

import com.hjx.springbootmybatis.enums.IResponseEnum;

/**
 * @Author: hjx
 * @Date: 2019/7/15
 * @Version 1.0
 */
public class BusinessException extends BaseException{

    private static final long serialVersionUID = 1L;

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }
}
