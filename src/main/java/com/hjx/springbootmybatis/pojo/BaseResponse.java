package com.hjx.springbootmybatis.pojo;

import com.hjx.springbootmybatis.enums.IResponseEnum;
import com.hjx.springbootmybatis.enums.exceptionenum.CommonResponseEnum;
import lombok.Data;

/**
 * 基础返回结果
 * @Author: hjx
 * @Date: 2019/7/15
 * @Version 1.0
 */
@Data
public class BaseResponse {
    /**
     * 返回码
     */
    protected int code;
    /**
     * 返回消息
     */
    protected String message;

    public BaseResponse() {
        // 默认创建成功的回应
        this(CommonResponseEnum.SUCCESS);
    }

    public BaseResponse(IResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
