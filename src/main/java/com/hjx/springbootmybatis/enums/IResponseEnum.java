package com.hjx.springbootmybatis.enums;

/**
 * 异常返回码枚举接口
 * @Author: hjx
 * @Date: 2019/7/15
 * @Version 1.0
 */
public interface IResponseEnum {
    /**
     * 获取返回码
     * @return 返回码
     */
    int getCode();

    /**
     * 获取返回信息
     * @return 返回信息
     */
    String getMessage();
}
