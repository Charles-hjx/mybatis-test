package com.hjx.springbootmybatis.common.exception;

import com.hjx.springbootmybatis.enums.IResponseEnum;
import lombok.Getter;

/**
 * @Author: hjx
 * @Date: 2019/7/15
 * @Version 1.0
 */
@Getter
public class BaseException extends RuntimeException{

    /**
     * 返回码
     */
    protected IResponseEnum responseEnum;

    /**
     * 异常消息参数
     */
    protected Object[] args;

    public BaseException(IResponseEnum responseEnum){
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public BaseException(int code,String msg){
        super(msg);
        this.responseEnum = new IResponseEnum() {
            @Override
            public int getCode() {
                return code;
            }

            @Override
            public String getMessage() {
                return msg;
            }
        };
    }

    public BaseException(IResponseEnum responseEnum,Object[] args,String message){
        super(message);
        this.responseEnum = responseEnum;
        this.args =args;
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause){
        super(message,cause);
        this.responseEnum = responseEnum;
        this.args =args;
    }
}
