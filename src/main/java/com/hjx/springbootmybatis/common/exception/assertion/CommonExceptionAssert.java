package com.hjx.springbootmybatis.common.exception.assertion;

import com.hjx.springbootmybatis.common.exception.ArgumentException;
import com.hjx.springbootmybatis.common.exception.BaseException;
import com.hjx.springbootmybatis.enums.IResponseEnum;

import java.text.MessageFormat;

/**
 * @Author: hjx
 * @Date: 2019/7/15
 * @Version 1.0
 */
public interface CommonExceptionAssert extends Assert , IResponseEnum {
    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);

        return new ArgumentException(this, args, msg, t);
    }
}
