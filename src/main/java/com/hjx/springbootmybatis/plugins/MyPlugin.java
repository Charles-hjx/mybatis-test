package com.hjx.springbootmybatis.plugins;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * @Author: hjx
 * @Date: 2019/8/14
 * @Version 1.0
 */
@Intercepts({@Signature(  //定义拦截点
        type= Executor.class,
        method = "update",
        args = {MappedStatement.class,Object.class})})
public class MyPlugin implements Interceptor {



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override //注册当前 Interceptor
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override //从外部 设置参数
    public void setProperties(Properties properties) {
        //可以声明一个 全局变量 在这里设置值 然后再 intercept() 中使用
    }
}
