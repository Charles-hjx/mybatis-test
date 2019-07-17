package com.hjx.springbootmybatis.common.exception.assertion;

import com.hjx.springbootmybatis.common.exception.BaseException;
import com.hjx.springbootmybatis.common.exception.BusinessException;
import com.hjx.springbootmybatis.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 *
 * 业务异常断言,只是做参数的整合
 *
 * @Author: hjx
 * @Date: 2019/7/15
 * @Version 1.0
 */
public interface BusinessExceptionAssert extends IResponseEnum , Assert {
    @Override
    default BaseException newException(Object... args){
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this,args,msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args){
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }
}
